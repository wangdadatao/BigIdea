package com.datao.bigidea.entity;

import java.io.Serializable;

/**
 * Created by 王 海涛 on 2017/4/7.
 * <p>
 * 壁纸实体类
 */
public class Wallpaper implements Serializable {

    private static final long serialVersionUID = 8428360057966457881L;

    private Integer id;

    private String baseUrl;

    private String name;

    private Long size;

    private String uuidName;

    private String MD5;

    private String qiniuUrl;

    private String litUrl;

    private String litUuid;

    private String litMD5;

    private String litName;

    private Long litSize;

    private String createTime;

    private Integer width;

    private Integer height;

    private Integer litWidth;

    private Integer litHeight;

    private String feature;

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getUuidName() {
        return uuidName;
    }

    public void setUuidName(String uuidName) {
        this.uuidName = uuidName;
    }

    public String getMD5() {
        return MD5;
    }

    public void setMD5(String MD5) {
        this.MD5 = MD5;
    }

    public String getQiniuUrl() {
        return qiniuUrl;
    }

    public void setQiniuUrl(String qiniuUrl) {
        this.qiniuUrl = qiniuUrl;
    }

    public String getLitUrl() {
        return litUrl;
    }

    public void setLitUrl(String litUrl) {
        this.litUrl = litUrl;
    }

    public String getLitUuid() {
        return litUuid;
    }

    public void setLitUuid(String litUuid) {
        this.litUuid = litUuid;
    }

    public String getLitMD5() {
        return litMD5;
    }

    public void setLitMD5(String litMD5) {
        this.litMD5 = litMD5;
    }

    public String getLitName() {
        return litName;
    }

    public void setLitName(String litName) {
        this.litName = litName;
    }


    public Long getLitSize() {
        return litSize;
    }

    public void setLitSize(Long litSize) {
        this.litSize = litSize;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getLitWidth() {
        return litWidth;
    }

    public void setLitWidth(Integer litWidth) {
        this.litWidth = litWidth;
    }

    public Integer getLitHeight() {
        return litHeight;
    }

    public void setLitHeight(Integer litHeight) {
        this.litHeight = litHeight;
    }
}
