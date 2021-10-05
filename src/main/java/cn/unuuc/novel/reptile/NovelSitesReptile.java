package cn.unuuc.novel.reptile;


import cn.unuuc.novel.entity.Novel;
import cn.unuuc.novel.entity.NovelUrl;
import cn.unuuc.novel.entity.Rule;
import cn.unuuc.novel.utils.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class NovelSitesReptile {

    /**
     * 获取小说名
     * @return
     */
    public String getNovelName(String html, Rule rule){
        Document parse = Jsoup.parse(html);
        String name = parse.select(rule.getNameRule()).text();
        return name;
    }

    /**
     * 获取小说分类
     */
    public String getNovelClassify(String html,Rule rule){
        Document parse = Jsoup.parse(html);
        String text = parse.select(rule.getClassifyRule()).text();
        return text;
    }


    /**
     * 获取小说作者
     */
    public String getNovelAuthor(String html,Rule rule){
        Document parse = Jsoup.parse(html);
        String text = parse.select(rule.getAuthorRule()).text();
        String replace = text.replace(" ", "").replace("作", "").replace("者", "").replace(":", "").replace("：", "");
        return replace;
    }

    /**
     * 获取小说封面图
     */
    public String getNovelCover(String html,Rule rule){
        Document parse = Jsoup.parse(html);
        String text = parse.select(rule.getCoverRule()).attr("src");
        return text;
    }


    /**
     * 获取小说简介
     */
    public String getNovelPreface(String html,Rule rule){
        Document parse = Jsoup.parse(html);
        String text = parse.select(rule.getIntroRule()).text();
        return text;
    }

    /**
     * 获取小说章节列表
     * @param html
     * @param rule
     * @return  <a href="/15/15003/6795763.html">第四章 牛有道（庆百盟，加更）</a>
     */
    public List<String> getNovelChaptersList(String html,Rule rule){
        ArrayList<String> list = new ArrayList<>();
        Document parse = Jsoup.parse(html);
        Elements select = parse.select(rule.getChapterRule());
        select.stream().forEach(e->list.add(e.toString()));
        return list;
    }

    /**
     * 获取小说列表规则
     * @param html
     * @param rule
     * @return  <a href="https://www.xbiquge.la/0/951/">伏天氏</a>
     */
    public List<String> getNovelList(String html,Rule rule){
        ArrayList<String> list = new ArrayList<>();
        Document parse = Jsoup.parse(html);
        Elements select = parse.select(rule.getNovelRule());
        select.stream().forEach(e->list.add(e.toString()));
        return list;
    }


    // 获取小说章节内容
    public String getNovelContent(String html,Rule rule){
        Document parse = Jsoup.parse(html);
        return parse.select(rule.getContentRule()).toString();
    }

    // 获取采集源站中所有小说
    public List<NovelUrl> getNovelAllList(Rule rule){
        LinkedList<NovelUrl> list = new LinkedList<>();
        // 小说所有列表的页面地址
        String novelListUrl = rule.getNovelListUrl();
        Map<String,String> headers = new HashMap<>();
        headers.put("Referer",rule.getIndexUrl());
        String html = HttpClientUtil.doGet(novelListUrl, headers);
        List<String> novelList = getNovelList(html, rule);
        novelList.stream().forEach(e->{
            Element a = Jsoup.parse(e).select("a").get(0);
            // 进行规则拼接
            String href = (rule.getJointNovelRule() == null||rule.getJointNovelRule()=="") ? a.attr("href"):rule.getJointNovelRule()+a.attr("href");
            String title = a.text().trim();
            NovelUrl novelUrl = new NovelUrl();
            novelUrl.setName(title);
            novelUrl.setUrl(href);
            list.add(novelUrl);
        });
        return list;
    }

}
