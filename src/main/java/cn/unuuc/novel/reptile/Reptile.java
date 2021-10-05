package cn.unuuc.novel.reptile;

import cn.unuuc.novel.entity.Chapter;
import cn.unuuc.novel.entity.Classify;
import cn.unuuc.novel.entity.Novel;
import cn.unuuc.novel.entity.Rule;
import cn.unuuc.novel.service.ChapterService;
import cn.unuuc.novel.service.ClassifyService;
import cn.unuuc.novel.service.NovelService;
import cn.unuuc.novel.service.RuleService;
import cn.unuuc.novel.socket.ConsoleWebSocket;
import cn.unuuc.novel.utils.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class Reptile {

    @Autowired
    NovelSitesReptile novelSitesReptile;

    @Autowired
    ClassifyService classifyService;

    @Autowired
    NovelService novelService;

    @Autowired
    ChapterService chapterService;

    @Autowired
    RuleService ruleService;

    @Autowired
    ConsoleWebSocket consoleWebSocket;

    /**
     * 根据url爬取小说基本信息，存入小说表中,如果小说以存在，则返回数据库中已存在的信息
     * @param url
     * @param rule
     * @return
     */
    public Novel reptileNovel(String url, Rule rule){
        // 发起 get 请求，获取网页文件
        String html = HttpClientUtil.doGet(url, null);
        if(html == null) return null;
        // 小说名
        String title = novelSitesReptile.getNovelName(html, rule);

        /**************************************************************/
        // 判断小说是否已存在，存在则返回已存在的小说信息
        Novel novelByName = novelService.getNovelByName(title);
        if(novelByName!=null) return novelByName;
        /**************************************************************/

        // 作者
        String author = novelSitesReptile.getNovelAuthor(html, rule);
        // 分类id
        int cId = reptileClassify(novelSitesReptile.getNovelClassify(html, rule));
        // 封面
        String coverImg = reptileCover(novelSitesReptile.getNovelCover(html, rule));
        // 前言
        String preface = novelSitesReptile.getNovelPreface(html, rule);
        Novel novel = new Novel(title, author, url, coverImg, cId, preface, rule.getId());
        novelService.insertNovel(novel);
        return  novel;
    }


    /**
     * 根据url获取小说所有章节，进行采集并持久化，外键为小说表主键
     * @param novel
     */
    public void reptileChapters(Novel novel){
        // 获取小说爬虫规则
        Rule rule = ruleService.getNovelById(novel.getRId());
        // 爬取小说主页，获取html
        Map<String,String> headers = new HashMap<>();
        headers.put("Referer",rule.getIndexUrl());
        String html = HttpClientUtil.doGet(novel.getIndexUrl(), headers);
        if(html == null) return;
        // 获取小说列表
        List<String> list = novelSitesReptile.getNovelChaptersList(html, rule);
        // 查询小说以持久化的章节数
        int count = chapterService.countChapter(novel.getId());
        // 如果获取最新章节数量 小于等于 已持久化的数量,则不进行此次采集
        if(list.size()-5 <= count){
            // 发送信息到WebSocket链接
            consoleWebSocket.sendMessageAll(novel.getTitle()+"无最新章节,此次采集已跳过!");
//            System.out.println(novel.getTitle()+"无最新章节,此次采集已跳过!");
            return;
        }
        // 开始采集小说章节
        list.stream().forEach(e->{
            Element a = Jsoup.parse(e).select("a").get(0);
            // 进行规则拼接
            String href = rule.getJointChapterRule() == null ? a.attr("href"):rule.getJointChapterRule()+a.attr("href");
            String title = a.text().trim();

            // 判断章节是否以存在,只有不存在情况下才会采集数据
            int countByName = chapterService.countChapterByName(title, novel.getId());
            if(countByName<=0){
                consoleWebSocket.sendMessageAll("正在采集-----"+novel.getTitle()+"-----"+title);
                // 获取章节内容页面的html
                String content = null;
                String chapterContentHtml = HttpClientUtil.doGet(href, headers);
                if(chapterContentHtml!=null){
                    content = novelSitesReptile.getNovelContent(chapterContentHtml, rule);
                    Chapter chapter = new Chapter(title,content,novel.getId());
                    chapterService.insertChapter(chapter);
                }else {
                    consoleWebSocket.sendMessageAll("失败,章节内容采集为空-----"+novel.getTitle()+"-----"+title+"---------------------------------------------------------------------------------------------");
                    System.out.println(title+"   失败");
                }
            }else {
                System.out.println(title+"   已存在");
            }
        });
        consoleWebSocket.sendMessageAll("已完成本次任务,将尝试下一个任务!");
    }



    /**
     * 查询数据库中是否存在改分类名称，无则添加返回主键
     * @param name
     * @return
     */
    public int reptileClassify(String name){
        Classify classify = classifyService.getClassifyByName(name);
        if(classify !=null)
            return classify.getId();
        Classify insert = new Classify(name);
        classifyService.insertClassify(insert);
        return insert.getId();
    }

    /**
     * 下载封面图片，返回uuid名，
     * @param href
     * @return
     */
    public String reptileCover(String href){
        if(href == null || href == "") return null;
        return HttpClientUtil.doGetDown(href, null);
    }


}
