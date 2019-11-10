package com.push.push.webSocket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/index")
@Component
public class WebSocketServer {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;


    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            System.out.println("onOpen发生错误："+e.getLocalizedMessage());
        }

    }

    @OnClose
    public void onClose(){
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();
        System.out.println("已关闭长连服务");
    }

    @OnMessage
    public void onMessage(String message, Session session){
        System.out.println("已收到消息");
        try {
            sendMessage("你好，已收到消息",session);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session,Throwable error){
        System.out.println("发生错误："+error.getLocalizedMessage());
    }

    public void sendMessage(String message,Session session) throws IOException {
        session.getBasicRemote().sendText(message);
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     * */
    public static void sendInfo(String message) throws IOException {
        System.out.println("进入发送方法");
        if (webSocketSet != null){
            System.out.println("消息条数为："+webSocketSet.size());
        }else {
            System.out.println("websocketset为空值");
        }
        for (WebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

}
