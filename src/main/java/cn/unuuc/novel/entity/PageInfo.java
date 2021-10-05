package cn.unuuc.novel.entity;

import lombok.Data;

import java.util.List;

@Data
public class PageInfo {
    private int total;// 数量
    private List<?> rows;
}
