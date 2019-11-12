package com.push.push.bean;

public class MessageBean {
    private String title;
    private String content;
    private String imageUrl;
    private int type;
    private String targetId;
    private int minYear;
    private int maxYear;
    private int channel = -1;
    private String id;



    public MessageBean(String title, String content, String imageUrl, int type, String targetId, int minYear, int maxYear, int channel, String id) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.type = type;
        this.targetId = targetId;
        this.minYear = minYear;
        this.maxYear = maxYear;
        this.channel = channel;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMinYear() {
        return minYear;
    }

    public void setMinYear(int minYear) {
        this.minYear = minYear;
    }

    public int getMaxYear() {
        return maxYear;
    }

    public void setMaxYear(int maxYear) {
        this.maxYear = maxYear;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
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
