package cn.unuuc.novel.entity;

import lombok.Data;

@Data
public class Rule {
    private Integer id;

    private String indexUrl;

    private String nameRule;

    private String classifyRule;

    private String authorRule;
    // 封面规则
    private String coverRule;
    // 前言规则
    private String introRule;
    // 小说规则，获取小说列表
    private String novelRule;
    // 章节规则，获取小说章节列表
    private String chapterRule;
    // 章节内容规则
    private String contentRule;
    // 小说列表拼接规则
    private String jointNovelRule;
    // 章节列表拼接内容，为null则不拼接
    private String jointChapterRule;
    // 小说列表地址
    private String novelListUrl;

    // 备注
    private String remark;

}
