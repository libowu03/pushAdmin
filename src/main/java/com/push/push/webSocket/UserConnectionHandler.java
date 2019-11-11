package com.push.push.webSocket;

import com.push.push.utils.WebSocketUtils;
import org.springframework.web.socket.*;

import javax.websocket.Session;
import java.util.concurrent.CopyOnWriteArraySet;

public class UserConnectionHandler implements WebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("用户连接成功");
        WebSocketUtils.addUser(session);
        //小管理员汇报当前在线人数
        WebSocketUtils.sendMessageToAdmin();
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        session.sendMessage(new TextMessage("连接成功"));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("用户连接失败");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("用户退出连接");
        WebSocketUtils.removeUser(session);
        //向管理员汇报当前人数
        WebSocketUtils.sendMessageToAdmin();

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }


}
