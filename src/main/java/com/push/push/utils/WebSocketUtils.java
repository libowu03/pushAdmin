package com.push.push.utils;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.Session;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

public class WebSocketUtils {
    /**
     * 静态变量，用来记录当前在线连接数。即返回前台的人数。
     */
    private static Long onlineCount = 0L;
    /**
     * 用来存放普通用户ID。
     */
    private static CopyOnWriteArraySet<String> userIdSet = new CopyOnWriteArraySet<>();
    /**
     * 用来存放普通用户Session和id。
     */
    private static CopyOnWriteArraySet<WebSocketSession> usersSessionEntitySet = new CopyOnWriteArraySet<>();
    /**
     * 用来存放管理员Session和id。
     */
    private static CopyOnWriteArraySet<WebSocketSession> adminSessionEntitySet = new CopyOnWriteArraySet<>();

    /**
     * 添加管理员
     * @param socketSession
     */
    public static synchronized void addAdmin(WebSocketSession socketSession){
        adminSessionEntitySet.add(socketSession);
    }

    /**
     * 添加普通用户
     * @param socketSession
     */
    public static synchronized void addUser(WebSocketSession socketSession){
        usersSessionEntitySet.add(socketSession);
    }

    /**
     * 删除管理员
     * @param webSocketSession
     */
    public static synchronized void removeAdmin(WebSocketSession webSocketSession){
        adminSessionEntitySet.remove(webSocketSession);
    }

    /**
     * 删除普通用户
     * @param webSocketSession
     */
    public static synchronized void removeUser(WebSocketSession webSocketSession){
        usersSessionEntitySet.remove(webSocketSession);
    }

    /**
     * 获取管理员在线人数
     * @return
     */
    public static synchronized int getAdminOnlineCount(){
        return adminSessionEntitySet.size();
    }

    /**
     * 获取普通用户在线人数
     * @return
     */
    public static synchronized int getUserOnlineCount(){
        return usersSessionEntitySet.size();
    }

    /**
     * 发送消息给管理员
     */
    public static void sendMessageToAdmin(){
        adminSessionEntitySet.forEach(webSocketSession -> {
            try {
                webSocketSession.sendMessage(new TextMessage("在线人数为："+getUserOnlineCount()));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("发送消息给管理员失败："+e.getLocalizedMessage());
            }
        });
    }


    /**
     * 发送消息给用户
     * @param msg
     */

    public static void sendMessageToUser(String msg){
        usersSessionEntitySet.forEach(webSocketSession -> {
            try {
                webSocketSession.sendMessage(new TextMessage(msg));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("发送消息给用户失败："+e.getLocalizedMessage());
            }
        });
    }

}
