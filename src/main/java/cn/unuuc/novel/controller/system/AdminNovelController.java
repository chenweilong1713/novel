package cn.unuuc.novel.controller.system;

import cn.unuuc.novel.entity.Novel;
import cn.unuuc.novel.entity.PageInfo;
import cn.unuuc.novel.service.NovelService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminNovelController {

    @Autowired
    NovelService novelService;

    /**
     * 分页查询
     * @param offset
     * @param limit
     * @return
     */
    @GetMapping("/novel/list")
    @ResponseBody
    public String novelList(String offset,String limit){

        if(offset == null || limit == null)
            return null;
        int novelCount = novelService.getCountNovelAll();
        List<Novel> novelList = novelService.getNovelList(Integer.parseInt(offset), Integer.parseInt(limit));
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(novelList);
        pageInfo.setTotal(novelCount);
        return JSON.toJSONString(pageInfo);
    }
}
