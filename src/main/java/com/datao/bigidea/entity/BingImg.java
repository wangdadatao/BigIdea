package com.datao.bigidea.entity;

import java.io.Serializable;

/**
 * Created by 王 海涛 on 2017/4/1.
 * <p>
 * bing 图片
 */
public class BingImg implements Serializable {

    private static final long serialVersionUID = -3101313486128395459L;

    private Integer id;

    private String url;

    private String createTime;

    private String urlMD5;

    private String copyRight;

    private String startDate;

    private String fullStartdate;

    private String endDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUrlMd5() {
        return urlMD5;
    }

    public void setUrlMd5(String urlMd5) {
        this.urlMD5 = urlMd5;
    }

    public String getCopyright() {
        return copyRight;
    }

    public void setCopyright(String copyright) {
        this.copyRight = copyright;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFullStartdate() {
        return fullStartdate;
    }

    public void setFullStartdate(String fullStartdate) {
        this.fullStartdate = fullStartdate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
