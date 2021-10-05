package cn.unuuc.novel.service.impl;

import cn.unuuc.novel.entity.Novel;
import cn.unuuc.novel.mapper.NovelMapper;
import cn.unuuc.novel.service.NovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NovelServiceImpl implements NovelService {

    @Autowired
    NovelMapper novelMapper;

    @Override
    public int insertNovel(Novel novel) {
        return novelMapper.insert(novel);
    }

    @Override
    public Novel getNovelByName(String title) {
        return novelMapper.getByName(title);
    }

    @Override
    public int getCountNovelAll() {
        return novelMapper.getCountAll();
    }

    @Override
    public List<Novel> getNovelList(Integer offset, Integer limit) {
        return novelMapper.selectPage(offset,limit);
    }
}
