package cn.unuuc.novel.service.impl;

import cn.unuuc.novel.entity.Rule;
import cn.unuuc.novel.mapper.RuleMapper;
import cn.unuuc.novel.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleServiceImpl implements RuleService {

    @Autowired
    RuleMapper ruleMapper;

    @Override
    public Rule getNovelById(Integer id) {
        return ruleMapper.getById(id);
    }

    @Override
    public List<Rule> getNovelAll() {
        return ruleMapper.getAll();
    }

    @Override
    public int countRuleAll() {
        return ruleMapper.countAll();
    }
}
