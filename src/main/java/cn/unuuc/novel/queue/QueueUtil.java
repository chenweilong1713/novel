package cn.unuuc.novel.queue;

import cn.unuuc.novel.entity.Config;
import cn.unuuc.novel.entity.Novel;
import cn.unuuc.novel.entity.Rule;
import cn.unuuc.novel.reptile.Reptile;
import cn.unuuc.novel.service.ConfigService;
import cn.unuuc.novel.service.RuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Queue;

@Slf4j
@Component
public class QueueUtil {

    @Autowired
    Reptile reptile;

    @Autowired
    RuleService ruleService;

    @Autowired
    ConfigService configService;


    @Autowired
    private static Reptile staticReptile;

    @Autowired
    private static RuleService staticRuleService;

    @Autowired
    private static ConfigService staticConfigService;

    @PostConstruct
    public void init() {
        staticReptile = reptile;
        staticRuleService = ruleService;
        staticConfigService = configService;
    }
    // 开启处理url线程
    public void startUrlThread(){
        Thread thread = QueueThreadUtil.getQueueThreadUtil().getUrlThread();
        if(thread == null){
            // 开启url队列线程，根据队列中的url解析成Novel对象，并将Novel放入小说队列中
            Thread urlThread = new Thread(() -> queueUrlRule());
            urlThread.setName("url_thread");
            urlThread.start();
            QueueThreadUtil.getQueueThreadUtil().setUrlThread(urlThread);
        }
    }
    // 开启处理小说线程
    public void startNovelThread(){
        Thread thread = QueueThreadUtil.getQueueThreadUtil().getNovelThread();
        if(thread == null){
            // 开启处理小说队列线程，最终数据采集则是在线程中完成的
            Thread novelThread = new Thread(() -> queueNovelHandler());
            novelThread.setName("novel_thread");
            novelThread.start();
            QueueThreadUtil.getQueueThreadUtil().setNovelThread(novelThread);
        }
    }


    // 项目启动开启采集工作，循环遍历 采集队列，存在任务则进行爬取
    private static void queueNovelHandler()  {
        log.info("章节采集线程已启动......");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            QueueThreadUtil.getQueueThreadUtil().setNovelThread(null);
            e.printStackTrace();
            log.info("在睡眠时中断，会导致novelThread无法置为null,已添加在此情况下的处理");
        }

        while (!Thread.currentThread().isInterrupted()){
            Queue<Novel> queue = QueueNovel.getQueueUtil().getQueue();
            Novel novel = queue.poll();
            if (novel!=null){
                staticReptile.reptileChapters(novel);
            }
            // 判断是否需在无任务下停止线程
            Config configById = staticConfigService.getConfigById(1);
            if(queue.peek() == null && configById.getNovelThreadFlag() == false){
                break;
            }
        }
        QueueThreadUtil.getQueueThreadUtil().setNovelThread(null);
    }

    // 开启url处理线程，将url信息解析为Novel对象，并将Novel对象放入采集队列中
    private static void queueUrlRule(){
        log.info("url处理线程已启动......");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            QueueThreadUtil.getQueueThreadUtil().setUrlThread(null);
            e.printStackTrace();
            log.info("在睡眠时中断，会导致urlThread无法置为null,已添加在此情况下的处理");
        }
        while (!Thread.currentThread().isInterrupted()){
            Queue<UrlRule> queue = QueueUrl.getQueueUrl().getQueue();
            UrlRule poll = queue.poll();
            if(poll != null){
                Rule novelById = staticRuleService.getNovelById(poll.getNId());
                Novel novel = staticReptile.reptileNovel(poll.getUrl(), novelById);
                QueueNovel.getQueueUtil().getQueue().offer(novel);
            }
            // 判断是否需在无任务下停止线程
            Config configById = staticConfigService.getConfigById(1);
            if(queue.peek() == null && configById.getUrlThreadFlag() == false){
                break;
            }
        }
        QueueThreadUtil.getQueueThreadUtil().setUrlThread(null);
    }
}
