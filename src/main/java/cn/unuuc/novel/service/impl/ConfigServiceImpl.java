package cn.unuuc.novel.service.impl;

import cn.unuuc.novel.entity.Config;
import cn.unuuc.novel.mapper.ConfigMapper;
import cn.unuuc.novel.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    ConfigMapper configMapper;

    @Override
    public Config getConfigById(Integer id) {
        return configMapper.getById(id);
    }

    @Override
    public int updateConfig(Config config) {
        return configMapper.update(config);
    }
}
