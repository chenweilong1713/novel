package cn.unuuc.novel.controller.system;

import cn.unuuc.novel.entity.NovelUrl;
import cn.unuuc.novel.entity.Rule;
import cn.unuuc.novel.queue.QueueUrl;
import cn.unuuc.novel.queue.UrlRule;
import cn.unuuc.novel.reptile.NovelSitesReptile;
import cn.unuuc.novel.service.RuleService;
import cn.unuuc.novel.utils.AjaxJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.List;
import java.util.Queue;


@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminReptileController {

    @Autowired
    RuleService ruleService;

    @Autowired
    NovelSitesReptile novelSitesReptile;

    /**
     * 将url放入url队列中，待url对象队列线程启动后将会执行
     * @param url
     * @param id
     * @return
     */
    @PostMapping("/offer/url/queue")
    @ResponseBody
    public AjaxJson offerUrlQueue(String url,Integer id){
        if(url == null || id == null || url == ""){
            return AjaxJson.getNotJur("参数为空");
        }
        Rule rule = ruleService.getNovelById(id);
        if(!url.contains(rule.getIndexUrl())){
            return AjaxJson.getNotJur("url与规则不匹配");
        }
        UrlRule urlRule = new UrlRule();
        urlRule.setUrl(url);
        urlRule.setNId(id);
        QueueUrl.getQueueUrl().getQueue().offer(urlRule);
        log.info(url+"  已添加至队列中!");
        return AjaxJson.getSuccess("已添加至任务队列中,在线程启动后进行处理!");
    }

    /**
     * 从规则中查找小说名字
     * @param name
     * @return
     */
    @PostMapping("/search")
    @ResponseBody
    public AjaxJson searchName(String name,Integer id){
        if(name == null || id == null || name == ""){
            return AjaxJson.getNotJur("参数为空");
        }
        // 根据规则id获取规则对象
        Rule rule = ruleService.getNovelById(id);
        List<NovelUrl> novelList = novelSitesReptile.getNovelAllList(rule);
        for (NovelUrl n:novelList){
            if(n.getName().equals(name)){
                UrlRule urlRule = new UrlRule();
                urlRule.setUrl(n.getUrl());
                urlRule.setNId(id);
                QueueUrl.getQueueUrl().getQueue().offer(urlRule);
                return AjaxJson.getSuccess("已查询到小说并添加至任务中!");
            }
        }
        // 获取规则中所有
        return AjaxJson.getNotJur("未查找到小说");
    }


    /**
     * 根据规则id遍历所有的小说url,全部放入url队列中
     * @param id
     * @return
     */
    @PostMapping("/add/url/queue")
    @ResponseBody
    public AjaxJson addQueueByRuleId(Integer id){
        Rule rule = ruleService.getNovelById(id);
        // 获取所有的NovelUrl
        List<NovelUrl> novelList = novelSitesReptile.getNovelAllList(rule);
        Queue<UrlRule> queue = QueueUrl.getQueueUrl().getQueue();
        Collection<UrlRule> collection = queue;
        novelList.stream().forEach(e->{
            UrlRule urlRule = new UrlRule();
            urlRule.setUrl(e.getUrl());
            urlRule.setNId(id);
            System.out.println(urlRule);
            queue.offer(urlRule);
        });
        return AjaxJson.getSuccess("已添加"+novelList.size()+"个任务至URL队列中");
    }


}
