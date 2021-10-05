package cn.unuuc.novel.queue;

public class QueueThreadUtil {
    private  Thread urlThread = null;
    private  Thread novelThread = null;

    private static QueueThreadUtil queueThreadUtil = null;

    private QueueThreadUtil(){

    }
    // 单例模式
    public static QueueThreadUtil getQueueThreadUtil(){
        if(queueThreadUtil == null){
            queueThreadUtil = new QueueThreadUtil();
            return queueThreadUtil;
        }
        return queueThreadUtil;
    }

    public Thread getUrlThread(){
        return urlThread;
    }

    public void setUrlThread(Thread thread){
        urlThread  = thread;
    }

    public Thread getNovelThread(){
        return novelThread;
    }

    public void setNovelThread(Thread thread){
        novelThread = thread;
    }
}
