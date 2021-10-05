package cn.unuuc.novel.service.impl;

import cn.unuuc.novel.entity.Chapter;
import cn.unuuc.novel.mapper.ChapterMapper;
import cn.unuuc.novel.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    ChapterMapper chapterMapper;

    @Override
    public int insertChapter(Chapter chapter) {
        return chapterMapper.insert(chapter);
    }

    @Override
    public int countChapter(Integer nId) {
        return chapterMapper.countByNId(nId);
    }

    @Override
    public int countChapterByName(String title, Integer nId) {
        return chapterMapper.countByTitleAndNId(title,nId);
    }

    @Override
    public int countChapterAll() {
        return chapterMapper.countAll();
    }
}
