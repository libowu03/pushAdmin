package com.push.push.webSocket;

import com.push.push.utils.WebSocketUtils;
import org.springframework.web.socket.*;

public class AdminHandler implements WebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("管理员连接成功");
        WebSocketUtils.addAdmin(session);
        WebSocketUtils.sendMessageToAdmin();
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("管理员连接异常");
        WebSocketUtils.removeAdmin(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("管理员退出连接");
        WebSocketUtils.removeAdmin(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}
