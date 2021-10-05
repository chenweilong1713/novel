package cn.unuuc.novel.queue;


import java.util.LinkedList;
import java.util.Queue;

public class QueueUrl {
    private static QueueUrl queueUrl = null;

    private Queue<UrlRule> queue = new LinkedList<>();


    private QueueUrl(){

    }
    // 懒汉式单例
    public static QueueUrl getQueueUrl(){
        if(queueUrl == null){
            queueUrl = new QueueUrl();
            return queueUrl;
        }
        return queueUrl;
    }

    public Queue<UrlRule> getQueue(){
        return queue;
    }
}
