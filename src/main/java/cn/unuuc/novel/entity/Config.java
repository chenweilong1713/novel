package cn.unuuc.novel.entity;

import lombok.Data;

@Data
public class Config {

    private Integer id;
    // 失败重试次数
    private Integer failTime;

    // 延迟事件
    private Integer delay;

    // 失败时增加的延迟
    private Integer failDelay;

    // URL处理线程无任务时状态    true 保持，false 停止
    private Boolean urlThreadFlag;

    // Novel处理线程无任务时状态  true 保持，false 停止
    private Boolean novelThreadFlag;
}
