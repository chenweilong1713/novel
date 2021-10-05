package cn.unuuc.novel.controller.system;

import cn.unuuc.novel.entity.Config;
import cn.unuuc.novel.entity.Novel;
import cn.unuuc.novel.entity.Rule;
import cn.unuuc.novel.queue.QueueNovel;
import cn.unuuc.novel.queue.QueueThreadUtil;
import cn.unuuc.novel.queue.QueueUrl;
import cn.unuuc.novel.queue.UrlRule;
import cn.unuuc.novel.service.ChapterService;
import cn.unuuc.novel.service.ConfigService;
import cn.unuuc.novel.service.NovelService;
import cn.unuuc.novel.service.RuleService;
import cn.unuuc.novel.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Queue;


@Controller
@RequestMapping("/admin")
public class AdminPageController {

    @Autowired
    NovelService novelService;

    @Autowired
    ChapterService chapterService;

    @Autowired
    RuleService ruleService;

    @Autowired
    ConfigService configService;

    @Value("${webSocketUrl}")
    String webSocketUrl;


    @GetMapping("")
    public String toAdminIndexPage(){
        return "admin/index";
    }

    @GetMapping("/main")
    public String toAdminMainPage(Model model){
        // 小说数量
        model.addAttribute("novel_count",novelService.getCountNovelAll());
        // 章节数量
        model.addAttribute("chapter_count",chapterService.countChapterAll());
        // Novel队列任务
        model.addAttribute("reptile_queue_count", QueueNovel.getQueueUtil().getQueue().size());
        // URL队列任务
        model.addAttribute("reptile_site_count",QueueUrl.getQueueUrl().getQueue().size());

        model.addAttribute("url_thread", QueueThreadUtil.getQueueThreadUtil().getUrlThread());
        model.addAttribute("novel_thread", QueueThreadUtil.getQueueThreadUtil().getNovelThread());
        // websocket连接地址
        model.addAttribute("web_socket_url",webSocketUrl);
        return "admin/main";
    }

    @GetMapping("/login")
    public String toAdminLoginPage(){
        return "admin/login";
    }

    @GetMapping("/pwd")
    public String toAdminPwd(){
        return "admin/pwd";
    }

    @GetMapping("/novel")
    public String toAdminNovel(){
        return "admin/novel";
    }

    @GetMapping("/reptile")
    public String toReptile(Model model){
        List<Rule> list = ruleService.getNovelAll();
        model.addAttribute("rule_list",list);
        return "admin/reptile";
    }

    @GetMapping("/reptile/config")
    public String toReptileConfig(Model model){
        Config configById = configService.getConfigById(1);
        model.addAttribute("config",configById);
        return "admin/reptile_config";
    }

    @GetMapping("/rule")
    public String toRule(Model model){
        model.addAttribute("rule_list",ruleService.getNovelAll());
        return "admin/rule";
    }

    @GetMapping("/auto/reptile")
    public String toAutoReptile(Model model){
        List<Rule> list = ruleService.getNovelAll();
        model.addAttribute("rule_list",list);
        return "admin/auto_reptile";
    }

    @GetMapping("/queue")
    public String toQueue(Model model){
        Queue<UrlRule> urlRuleQueue = QueueUrl.getQueueUrl().getQueue();
        Queue<Novel> novelQueue = QueueNovel.getQueueUtil().getQueue();
        model.addAttribute("url_queue",urlRuleQueue.size());
        model.addAttribute("novel_queue",novelQueue.size());
        return "admin/queue";
    }





}
