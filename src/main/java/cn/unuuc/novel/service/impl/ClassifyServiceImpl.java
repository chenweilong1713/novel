package cn.unuuc.novel.service.impl;

import cn.unuuc.novel.entity.Classify;
import cn.unuuc.novel.mapper.ClassifyMapper;
import cn.unuuc.novel.service.ClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassifyServiceImpl implements ClassifyService {

    @Autowired
    ClassifyMapper classifyMapper;

    @Override
    public int insertClassify(Classify classify) {
        return classifyMapper.insert(classify);
    }

    @Override
    public Classify getClassifyById(Integer id) {
        return classifyMapper.getById(id);
    }

    @Override
    public Classify getClassifyByName(String name) {
        return classifyMapper.getByName(name);
    }
}
