package cn.unuuc.novel.entity;

import lombok.Data;


/**
 * 小说表
 */
@Data
public class Novel {

    private Integer id;
    // 图书名称
    private String title;
    // 作者
    private String author;
    // 源地址(主页)
    private String indexUrl;
    // 封面
    private String coverImg;
    // 分类id
    private Integer cId;
    // 前言
    private String preface;
    // 规则id
    private Integer rId;

    private String className;



    public Novel(String title, String author, String index, String coverImg, Integer cId, String preface, Integer rId) {
        this.title = title;
        this.author = author;
        this.indexUrl = index;
        this.coverImg = coverImg;
        this.cId = cId;
        this.preface = preface;
        this.rId = rId;
    }

    public Novel() {
    }

    @Override
    public String toString() {
        return "Novel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", indexUrl='" + indexUrl + '\'' +
                ", coverImg='" + coverImg + '\'' +
                ", classifyId=" + cId +
                ", preface='" + preface + '\'' +
                ", rId=" + rId +
                '}';
    }
}
