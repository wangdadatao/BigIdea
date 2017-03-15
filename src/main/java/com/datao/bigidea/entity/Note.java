package com.datao.bigidea.entity;

import java.io.Serializable;

/**
 * Created by 王 海涛 on 2016/11/25.
 */
public class Note implements Serializable {

    private static final long serialVersionUID = 4892307210777122459L;

    private Integer id;

    private Integer userId;

    private String title;

    private String createTime;

    private String createIP;

    private String type;

    private String content;

    private String contentNoElement;

    private String lastChangeTime;

    private String lastChangeIP;

    private Integer status;

    private Integer clickNum;

    private Integer replyNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateIP() {
        return createIP;
    }

    public void setCreateIP(String createIP) {
        this.createIP = createIP;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentNoElement() {
        return contentNoElement;
    }

    public void setContentNoElement(String contentNoElement) {
        this.contentNoElement = contentNoElement;
    }

    public String getLastChangeTime() {
        return lastChangeTime;
    }

    public void setLastChangeTime(String lastChangeTime) {
        this.lastChangeTime = lastChangeTime;
    }

    public String getLastChangeIP() {
        return lastChangeIP;
    }

    public void setLastChangeIP(String lastChangeIP) {
        this.lastChangeIP = lastChangeIP;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getClickNum() {
        return clickNum;
    }

    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
    }

    public Integer getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
    }
}
