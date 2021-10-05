package cn.unuuc.novel.service;

import cn.unuuc.novel.entity.Novel;

import java.util.List;

public interface NovelService {

    // 新增
    int insertNovel(Novel novel);

    Novel getNovelByName(String title);

    // 获取小说数量
    int getCountNovelAll();

    // 分页查询
    List<Novel> getNovelList(Integer offset, Integer limit);


}
