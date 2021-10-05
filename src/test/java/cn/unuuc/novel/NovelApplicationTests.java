package cn.unuuc.novel;

import cn.unuuc.novel.entity.*;
import cn.unuuc.novel.queue.QueueThreadUtil;
import cn.unuuc.novel.queue.QueueUtil;
import cn.unuuc.novel.reptile.NovelSitesReptile;
import cn.unuuc.novel.reptile.Reptile;
import cn.unuuc.novel.service.ChapterService;
import cn.unuuc.novel.service.ClassifyService;
import cn.unuuc.novel.service.ConfigService;
import cn.unuuc.novel.service.RuleService;
import cn.unuuc.novel.utils.HttpClientUtil;
import cn.unuuc.novel.queue.QueueNovel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Queue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NovelApplicationTests {

    @Autowired
    NovelSitesReptile novelSitesReptile;

    @Autowired
    RuleService ruleService;

    @Autowired
    ClassifyService classifyService;

    @Autowired
    Reptile reptile;

    // 测试获取小说列表
    @Test
    void testReptile() {
        Rule novelById = ruleService.getNovelById(1);
        String html = HttpClientUtil.doGet(novelById.getNovelListUrl(), null);
        List<String> novelList = novelSitesReptile.getNovelList(html, novelById);
        System.out.println(novelList.size());
        novelList.stream().forEach(e->{
            Element a = Jsoup.parse(e).select("a").get(0);
            // 进行规则拼接
            String href = novelById.getJointNovelRule() == null ? a.attr("href"):novelById.getJointNovelRule()+a.attr("href");
            System.out.println(href+"      " + a.text());
        });
    }

    // 测试分类的新增和查询
    @Test
    void  testClassify(){
        Classify classify = new Classify();
        classify.setName("玄幻小说");
//        int i = classifyService.insertClassify(classify);
//        System.out.println(classify);
//        Classify classifyById = classifyService.getClassifyById(1);
//        System.out.println(classifyById);
//        int count = classifyService.getCountByName("玄幻小说");
//        System.out.println(count);
    }


    // 测试爬取小说信息
    @Test
    void testReptileNovel(){
        Rule rule = ruleService.getNovelById(1);
        Novel novel = reptile.reptileNovel("https://www.xbiquge.la/7/7931/", rule);
        System.out.println(novel);
    }

    // 测试获取配置文件值
    @Test
    void testProperties(){
    }

    // 测试队列
    @Test
    void  testQueue() {
        QueueNovel queueUtil1 = QueueNovel.getQueueUtil();
        QueueNovel queueUtil2 = QueueNovel.getQueueUtil();
        System.out.println(queueUtil1 == queueUtil2);
        System.out.println(queueUtil1.getQueue() == queueUtil2.getQueue());
    }

    @Autowired
    ChapterService chapterService;
    // 测试章节表
    @Test
    void testChapter(){
        Chapter chapter = new Chapter("测试","内容",1);
//        int i = chapterService.insertChapter(chapter);
//        System.out.println(chapter);
        int i = chapterService.countChapter(1);
        System.out.println(i);
    }

    // 测试章节列表
    @Test
    void testChapterList(){
        Rule novelById = ruleService.getNovelById(1);
        String html = HttpClientUtil.doGet("https://www.xbiquge.la/7/7931/", null);
        List<String> novelChaptersList = novelSitesReptile.getNovelChaptersList(html, novelById);
        System.out.println(novelChaptersList.size());
        novelChaptersList.stream().forEach(e->{
            Element a = Jsoup.parse(e).select("a").get(0);
            // 进行规则拼接
            String href = novelById.getJointChapterRule() == null ? a.attr("href"):novelById.getJointChapterRule()+a.attr("href");
            System.out.println(href+"      " + a.text());
        });
    }

    //测试小说整体爬取
    @Test
    void test09(){
        Rule rule = ruleService.getNovelById(1);
        Novel novel = reptile.reptileNovel("https://www.xbiquge.la/15/15409/", rule);
        reptile.reptileChapters(novel);

    }

    // 测试新的规则列表
    @Test
    void testNewRule(){
        Rule rule = new Rule();
        rule.setChapterRule("#list dt:nth-of-type(2)~dd>a");
        String s = HttpClientUtil.doGet("http://www.b520.cc/14_14602/", null);
        List<String> novelChaptersList = novelSitesReptile.getNovelChaptersList(s, rule);
        System.out.println(novelChaptersList.size());
        novelChaptersList.stream().forEach(System.out::println);
    }
    @Test
    void testNewRule1(){
        Rule rule = new Rule();
        rule.setAuthorRule("#info>p:nth-of-type(1)");
        String s = HttpClientUtil.doGet("http://www.b520.cc/14_14602/", null);
        String novelAuthor = novelSitesReptile.getNovelAuthor(s, rule);
        System.out.println(novelAuthor);
    }
    @Test
    void testNewRuleCover(){
        Rule rule = new Rule();
        rule.setCoverRule("#fmimg>img");
        String s = HttpClientUtil.doGet("http://www.b520.cc/14_14602/", null);
        String novelCover = novelSitesReptile.getNovelCover(s, rule);
        System.out.println(novelCover);
    }
    @Test
    void testNewRuleClassify(){
        Rule rule = new Rule();
        rule.setChapterRule("#list dt:nth-of-type(2)~dd>a");
        String s = HttpClientUtil.doGet("http://www.b520.cc/14_14621/", null);
        List<String> novelList = novelSitesReptile.getNovelChaptersList(s, rule);
        System.out.println(novelList.size());
        novelList.stream().forEach(System.out::println);
    }

    @Test
    void testQu(){
        Queue<Novel> queue1 = QueueNovel.getQueueUtil().getQueue();
        Queue<Novel> queue2 = QueueNovel.getQueueUtil().getQueue();
        System.out.println(queue1 == queue2);
    }

    @Autowired
    QueueUtil queueUtil;

    // 测试线程
    @Test
    void testThread(){
        Thread urlThread1 = QueueThreadUtil.getQueueThreadUtil().getUrlThread();
        System.out.println(urlThread1);
        queueUtil.startUrlThread();
        Thread urlThread2 = QueueThreadUtil.getQueueThreadUtil().getUrlThread();
        System.out.println(urlThread2);
        Thread novelThread = QueueThreadUtil.getQueueThreadUtil().getNovelThread();
        System.out.println(novelThread);
        queueUtil.startNovelThread();
        System.out.println(QueueThreadUtil.getQueueThreadUtil().getNovelThread());
    }

    @Test
    void testString(){
        List<NovelUrl> novelList = novelSitesReptile.getNovelAllList(ruleService.getNovelById(2));
        novelList.stream().forEach(System.out::println);
    }
    @Autowired
    ConfigService configService;

    @Test
    void testConfig(){

    }


}
