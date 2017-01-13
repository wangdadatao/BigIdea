package com.datao.bigidea.entity;

import java.io.Serializable;

/**
 * Created by 王 海涛 on 2017/1/10.
 */
public class Movie implements Serializable {

    private static final long serialVersionUID = 8754704844529637444L;

    private String id;

    private String doubanID;

    private String doubanUrl;

    private String name;

    private String director;

    private String scriptwriter;

    private String protagonist;

    private String type;

    private String country;

    private String language;

    private String releaseTime;

    private String longTime;

    private String otherName;

    private String source;

    private String fiveStar;

    private String fourStar;

    private String threeStar;

    private String twoStar;

    private String oneStar;

    private String intro;

    private String img;

    private String json;

    public String getDoubanID() {
        return doubanID;
    }

    public void setDoubanID(String doubanID) {
        this.doubanID = doubanID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoubanUrl() {
        return doubanUrl;
    }

    public void setDoubanUrl(String doubanUrl) {
        this.doubanUrl = doubanUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getProtagonist() {
        return protagonist;
    }

    public void setProtagonist(String protagonist) {
        this.protagonist = protagonist;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getLongTime() {
        return longTime;
    }

    public void setLongTime(String longTime) {
        this.longTime = longTime;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getFiveStar() {
        return fiveStar;
    }

    public void setFiveStar(String fiveStar) {
        this.fiveStar = fiveStar;
    }

    public String getFourStar() {
        return fourStar;
    }

    public void setFourStar(String fourStar) {
        this.fourStar = fourStar;
    }

    public String getThreeStar() {
        return threeStar;
    }

    public void setThreeStar(String threeStar) {
        this.threeStar = threeStar;
    }

    public String getTwoStar() {
        return twoStar;
    }

    public void setTwoStar(String twoStar) {
        this.twoStar = twoStar;
    }

    public String getOneStar() {
        return oneStar;
    }

    public void setOneStar(String oneStar) {
        this.oneStar = oneStar;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getScriptwriter() {
        return scriptwriter;
    }

    public void setScriptwriter(String scriptwriter) {
        this.scriptwriter = scriptwriter;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
