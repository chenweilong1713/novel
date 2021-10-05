package cn.unuuc.novel.controller.system;

import cn.unuuc.novel.queue.QueueThreadUtil;
import cn.unuuc.novel.queue.QueueUtil;
import cn.unuuc.novel.utils.AjaxJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * url线程和novel线程开关的controller
 * 对于这两个线程是不会进行重复创建的，queue包下创建了一个单例类用于控制线程的开关
 */
@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminThreadController {

    @Autowired
    QueueUtil queueUtil;


    // 开启url线程
    @GetMapping("/start/url")
    @ResponseBody
    public AjaxJson startUrlThread(){
        queueUtil.startUrlThread();
        return AjaxJson.getSuccess("Url处理线程已开启");
    }

    // 开启novel线程
    @GetMapping("/start/novel")
    @ResponseBody
    public AjaxJson startNovelThread(){
        queueUtil.startNovelThread();
        return AjaxJson.getSuccess("Novel处理线程已开启");
    }

    // 关闭url线程
    @GetMapping("/close/url")
    @ResponseBody
    public AjaxJson closeUrlThread(){
        Thread thread = QueueThreadUtil.getQueueThreadUtil().getUrlThread();
        if (thread!=null){
            thread.interrupt();
            log.info("url线程已关闭");
        }
        return AjaxJson.getSuccess("Url线程已关闭");
    }

    // 关闭novel线程
    @GetMapping("/close/novel")
    @ResponseBody
    public AjaxJson closeNovelThread(){
        Thread thread = QueueThreadUtil.getQueueThreadUtil().getNovelThread();
        if (thread!=null){
            thread.interrupt();
            log.info("novel线程已关闭");
        }
        return AjaxJson.getSuccess("Novel线程已关闭");
    }

}
