package cn.unuuc.novel.service;

import cn.unuuc.novel.entity.Rule;

import java.util.List;

public interface RuleService {

    // 根据id获取rule对象
    Rule getNovelById(Integer id);

    // 获取所有的Rule,返回list
    List<Rule> getNovelAll();

    // 获取总数
    int countRuleAll();
}
