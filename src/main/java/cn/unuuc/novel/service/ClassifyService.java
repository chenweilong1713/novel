package cn.unuuc.novel.service;

import cn.unuuc.novel.entity.Classify;

public interface ClassifyService {

    // 新增 分类
    int insertClassify(Classify classify);

    // 根据id获取分类
    Classify getClassifyById(Integer id);

    // 根据name查询改分类数量
    Classify getClassifyByName(String name);

}
