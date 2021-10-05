package cn.unuuc.novel.service;

import cn.unuuc.novel.entity.Config;

public interface ConfigService {
    Config getConfigById(Integer id);

    int updateConfig(Config config);
}
