package com.datao.bigidea.entity;

import java.io.Serializable;

/**
 * Created by 王 海涛 on 2016/11/24.
 */
public class WordsTFIDF implements Serializable {

    private static final long serialVersionUID = 1998594194629626361L;

    private Integer id;

    private Integer blogID;

    private String words;

    private Integer thisNum;

    private Integer articleNum;

    private Integer allNum;

    private Double TF;

    private Double IDF;

    private Double TFIDF;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBlogID() {
        return blogID;
    }

    public void setBlogID(Integer blogID) {
        this.blogID = blogID;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }


    public Integer getThisNum() {
        return thisNum;
    }

    public void setThisNum(Integer thisNum) {
        this.thisNum = thisNum;
    }

    public Integer getArticleNum() {
        return articleNum;
    }

    public void setArticleNum(Integer articleNum) {
        this.articleNum = articleNum;
    }

    public Integer getAllNum() {
        return allNum;
    }

    public void setAllNum(Integer allNum) {
        this.allNum = allNum;
    }

    public Double getTF() {
        return TF;
    }

    public void setTF(Double TF) {
        this.TF = TF;
    }

    public Double getIDF() {
        return IDF;
    }

    public void setIDF(Double IDF) {
        this.IDF = IDF;
    }

    public Double getTFIDF() {
        return TFIDF;
    }

    public void setTFIDF(Double TFIDF) {
        this.TFIDF = TFIDF;
    }
}
