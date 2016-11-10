package com.datao.bigidea.serviceImpl;

import com.datao.bigidea.utils.contentextractor.ContentExtractor;
import com.datao.bigidea.utils.contentextractor.News;
import com.datao.bigidea.serviceImpl.service.ApiService;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class ApiServiceImpl extends BaseService implements ApiService {

    /**
     * 根据URL获取网页正文
     *
     * @param url
     * @return
     */
    @Override
    public Map<String, String> getUrlElement(String url) throws Exception {
        captchaParams(url);
        Map<String, String> result = Maps.newHashMap();
        News news = ContentExtractor.getNewsByUrl(url);

        result.put("url",news.getUrl());
        result.put("content",news.getContent());
        result.put("contentElement",news.getContentElement().toString());
        result.put("time",news.getTime());
        result.put("title",news.getTitle());
        return result;
    }
}
