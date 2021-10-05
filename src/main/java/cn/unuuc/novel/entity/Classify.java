package cn.unuuc.novel.entity;

import lombok.Data;

@Data
public class Classify {
    private Integer id;
    private String name;

    public Classify() {
    }

    public Classify(String name) {
        this.name = name;
    }
}
