package cn.unuuc.novel.entity;

import lombok.Data;

/**
 * 章节表
 */
@Data
public class Chapter {


    private Integer id;

    // 章节标题
    private String title;

    // 章节内容
    private String content;

    private Integer nId;

    public Chapter() {
    }

    public Chapter(String title, String content, Integer nId) {
        this.title = title;
        this.content = content;
        this.nId = nId;
    }

}
