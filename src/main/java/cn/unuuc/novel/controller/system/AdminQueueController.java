package cn.unuuc.novel.controller.system;

import cn.unuuc.novel.entity.Novel;
import cn.unuuc.novel.queue.QueueNovel;
import cn.unuuc.novel.queue.QueueUrl;
import cn.unuuc.novel.queue.UrlRule;
import cn.unuuc.novel.utils.AjaxJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Queue;

@Controller
@RequestMapping("/admin")
public class AdminQueueController {

    // 清空url队列
    @GetMapping("/remove/url/all")
    @ResponseBody
    public AjaxJson removeAllUrl(){
        Queue<UrlRule> queue = QueueUrl.getQueueUrl().getQueue();
        while (queue.peek()!=null){
            queue.poll();
        }
        return AjaxJson.getSuccess("清除成功!");
    }

    // 清空novel队列
    @GetMapping("/remove/url/novel")
    @ResponseBody
    public AjaxJson removeAllNovel(){
        Queue<Novel> queue = QueueNovel.getQueueUtil().getQueue();
        while (queue.peek()!=null){
            queue.poll();
        }
        return AjaxJson.getSuccess("清除成功!");
    }
}
