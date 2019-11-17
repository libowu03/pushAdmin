package com.push.push.bean;

public class MessageBean {
    private String title;
    private String content;
    private String imageUrl;
    private int type;
    private String targetId;
    private String id;
    private int channel;
    private String version;
    private String area;
    private String language;
    private String vip;
    private String age;

    public MessageBean(String title, String content, String imageUrl, int type, String targetId, String id, int channel, String version, String area, String language, String vip, String age) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.type = type;
        this.targetId = targetId;
        this.id = id;
        this.version = version;
        this.area = area;
        this.language = language;
        this.vip = vip;
        this.age = age;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
