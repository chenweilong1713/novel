package cn.unuuc.novel.queue;


import cn.unuuc.novel.entity.Novel;

import java.util.LinkedList;
import java.util.Queue;

public class QueueNovel {

    private static QueueNovel queueNovel = null;

    private Queue<Novel> queue = new LinkedList<>();


    private QueueNovel(){

    }
    // 懒汉式单例
    public static QueueNovel getQueueUtil(){
        if(queueNovel == null){
            queueNovel = new QueueNovel();
            return queueNovel;
        }
        return queueNovel;
    }

    public Queue<Novel> getQueue(){
        return queue;
    }

}
