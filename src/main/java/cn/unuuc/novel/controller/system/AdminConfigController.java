package cn.unuuc.novel.controller.system;

import cn.unuuc.novel.entity.Config;
import cn.unuuc.novel.service.ConfigService;
import cn.unuuc.novel.utils.AjaxJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class AdminConfigController {

    @Autowired
    ConfigService configService;

    @PostMapping("/update/config")
    @ResponseBody
    public AjaxJson updateConfig(Config config){
        config.setId(1);
        configService.updateConfig(config);
        return AjaxJson.getSuccess("更新成功!");
    }
}
