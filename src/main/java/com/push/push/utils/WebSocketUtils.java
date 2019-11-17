package com.push.push.utils;

import com.google.gson.Gson;
import com.push.push.bean.MessageBean;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

public class WebSocketUtils {
    /**
     * 静态变量，用来记录当前在线连接数。即返回前台的人数。
     */
    private static Long onlineCount = 0L;
    /**
     * 用来存放普通用户Session和id。
     */
    private static CopyOnWriteArraySet<WebSocketSession> usersSessionSet = new CopyOnWriteArraySet<>();
    /**
     * 用来存放管理员Session和id。
     */
    private static CopyOnWriteArraySet<WebSocketSession> adminSessionSet = new CopyOnWriteArraySet<>();

    /**
     * 添加管理员
     * @param socketSession
     */
    public static synchronized void addAdmin(WebSocketSession socketSession){
        adminSessionSet.add(socketSession);
    }

    /**
     * 添加普通用户
     * @param socketSession
     */
    public static synchronized void addUser(WebSocketSession socketSession){
        usersSessionSet.add(socketSession);
    }

    /**
     * 删除管理员
     * @param webSocketSession
     */
    public static synchronized void removeAdmin(WebSocketSession webSocketSession){
        adminSessionSet.remove(webSocketSession);
    }

    /**
     * 删除普通用户
     * @param webSocketSession
     */
    public static synchronized void removeUser(WebSocketSession webSocketSession){
        usersSessionSet.remove(webSocketSession);
    }

    /**
     * 获取管理员在线人数
     * @return
     */
    public static synchronized int getAdminOnlineCount(){
        return adminSessionSet.size();
    }

    /**
     * 获取普通用户在线人数
     * @return
     */
    public static synchronized int getUserOnlineCount(){
        return usersSessionSet.size();
    }

    /**
     * 发送消息给管理员
     */
    public static void sendMessageToAdmin(){
        adminSessionSet.forEach(webSocketSession -> {
            try {
                webSocketSession.sendMessage(new TextMessage("在线人数为："+getUserOnlineCount()));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("发送消息给管理员失败："+e.getLocalizedMessage());
            }
        });
    }

    /**
     * 发送消息给管理员
     */
    public static void sendMessageToAdmin(String msg){
        adminSessionSet.forEach(webSocketSession -> {
            try {
                webSocketSession.sendMessage(new TextMessage(msg));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("发送消息给管理员失败："+e.getLocalizedMessage());
            }
        });
    }


    /**
     * 发送消息给所有用户
     * @param msg
     */

    public static void sendMessageToUser(String msg){
        usersSessionSet.forEach(usersSessionSet->{
            try {
                usersSessionSet.sendMessage(new TextMessage(msg));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("消息发送失败");
            }
        });
    }

    /**
     * 发送给某个用户
     */

    public static void sendMessageToUserForSingle(String msg){
        Gson gson = new Gson();
        MessageBean messageBean = gson.fromJson(msg,MessageBean.class);
        usersSessionSet.forEach(webSocketSession -> {
            String[] path = webSocketSession.getUri().getQuery().split("&");
            HashMap<String,String> map = new HashMap<>();
            for (int i=0;i<path.length;i++){
                String[] para= path[i].split("=");
                map.put(para[0],para[1]);
            }
            if (map.get("id").equals(messageBean.getTargetId())){
                try {
                    webSocketSession.sendMessage(new TextMessage(msg));
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("发送消息给用户失败："+e.getLocalizedMessage());
                }
            }

        });
    }

    /**
     * 发送给某个渠道
     */

    public static void sendMessageToUserForChannel(String msg){
        Gson gson = new Gson();
        MessageBean messageBean = gson.fromJson(msg,MessageBean.class);
        usersSessionSet.forEach(webSocketSession -> {
            String[] path = webSocketSession.getUri().getQuery().split("&");
            HashMap<String,String> map = new HashMap<>();
            for (int i=0;i<path.length;i++){
                String[] para= path[i].split("=");
                map.put(para[0],para[1]);
            }
            if (Integer.valueOf(map.get("channel")) == messageBean.getChannel()){
                try {
                    webSocketSession.sendMessage(new TextMessage(msg));
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("发送消息给用户失败："+e.getLocalizedMessage());
                }
            }

        });
    }


    /**
     * 通过条件发送信息
     */

    public static void sendMessageToUserForCondition(String msg){
        Gson gson = new Gson();
        MessageBean messageBean = gson.fromJson(msg,MessageBean.class);
        int channel = messageBean.getChannel();
        String age = messageBean.getAge();
        String version = messageBean.getVersion();
        String area = messageBean.getArea();
        String language = messageBean.getLanguage();
        String vip = messageBean.getVip();
        int minAge = -1;
        int maxAge = -1;
        if (age != null){
            minAge = Math.min(Integer.parseInt(age.split("-")[0]),Integer.parseInt(age.split("-")[1]));
            maxAge = Math.max(Integer.parseInt(age.split("-")[0]),Integer.parseInt(age.split("-")[1]));
        }


        Iterator<WebSocketSession> iterator = usersSessionSet.iterator();
        while (iterator.hasNext()){
            WebSocketSession session = iterator.next();
            String[] path = session.getUri().getQuery().split("&");
            HashMap<String,String> map = new HashMap<>();
            for (int i=0;i<path.length;i++){
                String[] para= path[i].split("=");
                map.put(para[0],para[1]);
            }
            int userAge = map.get("age")==null?-1:Integer.valueOf(map.get("age"));
            if (version == null){
                version = map.get("version");
            }
            if (area == null){
                area = map.get("area");
            }
            if (language == null){
                language = map.get("language");
            }
            if (vip == null){
                vip = map.get("vip");
            }
            if (age == null){
                age = map.get("age");
                minAge = Integer.valueOf(age);
                maxAge = Integer.valueOf(age);
            }
            System.out.println(version.equals(map.get("version"))+","+
                    String.valueOf(channel).equals(map.get("channel")) +","+
                    area.equals(map.get("area")) +","+
                    language.equals(map.get("language")) +","+
                    vip.equals(map.get("vip")) +","+
                    (minAge<=Integer.valueOf(map.get("age"))) +","+
                    (maxAge>=Integer.valueOf(map.get("age"))    ));

            if (version.equals(map.get("version")) &&
                    String.valueOf(channel).equals(map.get("channel")) &&
                    area.equals(map.get("area")) &&
                    language.equals(map.get("language")) &&
                    vip.equals(map.get("vip")) &&
                    minAge<=Integer.valueOf(map.get("age")) &&
                    maxAge>=Integer.valueOf(map.get("age"))){
                try {
                    session.sendMessage(new TextMessage(msg));
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("发送消息给用户失败："+e.getLocalizedMessage());
                }
            }
            map.clear();

        }
        usersSessionSet.forEach(webSocketSession -> {



          /*  if (Integer.valueOf(map.get("channel")) == messageBean.getChannel() && Integer.valueOf(map.get("age"))>messageBean.getMinYear() && Integer.valueOf(map.get("age"))<messageBean.getMaxYear()){
                try {
                    webSocketSession.sendMessage(new TextMessage(msg));
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("发送消息给用户失败："+e.getLocalizedMessage());
                }
            }*/

        });
    }
}
