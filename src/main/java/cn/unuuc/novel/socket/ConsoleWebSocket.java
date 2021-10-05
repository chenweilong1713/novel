package cn.unuuc.novel.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ServerEndpoint(value = "/novel/socket")
@Component
public class ConsoleWebSocket {

    /** 记录当前在线连接数 */
    private static AtomicInteger onlineCount = new AtomicInteger(0);

    /** 存放所有在线的客户端 */
    private static Map<String, Session> clients = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        onlineCount.incrementAndGet(); // 在线数加1
        clients.put(session.getId(), session);
        log.info("有新连接加入：{}，当前在线人数为：{}", session.getId(), onlineCount.get());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        onlineCount.decrementAndGet(); // 在线数减1
        clients.remove(session.getId());
        log.info("有一连接关闭：{}，当前在线人数为：{}", session.getId(), onlineCount.get());
    }

    /**
     * 收到客户端消息  后调用的方法
     * @param message
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("服务端收到客户端[{}]的消息:{}", session.getId(), message);
        this.sendMessage("Hello, " + message, session);
    }

    /**
     * 服务端发送消息给客户端
     */
    public void sendMessage(String message, Session toSession) {
        try {
            log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败：{}", e);
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 群发消息
     * @param message
     */
    public void sendMessageExcludeMe(String message, Session fromSession) {
        for (Map.Entry<String, Session> sessionEntry : clients.entrySet()) {
            Session toSession = sessionEntry.getValue();
            // 排除掉自己
            if (!fromSession.getId().equals(toSession.getId())) {
                log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
                toSession.getAsyncRemote().sendText(message);
            }
        }
    }

    /**
     * 群发消息,包括自己
     * @param message
     */
    public void sendMessageAll(String message) {
        for (Map.Entry<String, Session> sessionEntry : clients.entrySet()) {
            Session toSession = sessionEntry.getValue();
            log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
            toSession.getAsyncRemote().sendText(message);
        }
    }
}
