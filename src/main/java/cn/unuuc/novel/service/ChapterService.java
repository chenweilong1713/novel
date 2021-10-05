package cn.unuuc.novel.service;

import cn.unuuc.novel.entity.Chapter;

public interface ChapterService {

    // 插入
    int insertChapter(Chapter chapter);

    // 根据小说外键查询章节数量
    int countChapter(Integer nId);

    // 根据章节名称和小说外键查询章节是否存在
    int countChapterByName(String title,Integer nId);

    // 获取小说章节总数
    int countChapterAll();
}
