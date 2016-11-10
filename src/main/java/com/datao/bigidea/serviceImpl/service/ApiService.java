package com.datao.bigidea.serviceImpl.service;

import com.datao.bigidea.utils.contentextractor.News;

import java.util.Map;


public interface ApiService {

    /**
     * 根据URL 获取网页正文
     *
     * @param url
     * @return
     */
    Map<String,String> getUrlElement(String url) throws Exception;
}
