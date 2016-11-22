package com.datao.bigidea.entity;

import org.jsoup.nodes.Element;

import java.io.Serializable;

public class News implements Serializable {

    private static final long serialVersionUID = 281080545417377L;

    private Integer id;

    private String url = null;

    private String title = null;

    private String content = null;

    private String time = null;

    private String contentEle;

    private String createTime;

    private String type;

    private Element contentElement = null;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        if (content == null) {
            if (contentElement != null) {
                content = contentElement.text();
            }
        }
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Element getContentElement() {
        return contentElement;
    }

    public void setContentElement(Element contentElement) {
        this.contentElement = contentElement;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContentEle() {
        return contentEle;
    }

    public void setContentEle(String contentEle) {
        this.contentEle = contentEle;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "URL:\n" + url + "\nTITLE:\n" + title + "\nTIME:\n" + time + "\nCONTENT:\n" + getContent() + "\nCONTENT(SOURCE):\n" + contentElement;
    }

}
