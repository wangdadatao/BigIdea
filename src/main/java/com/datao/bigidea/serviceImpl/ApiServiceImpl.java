package com.datao.bigidea.serviceImpl;

import com.datao.bigidea.exception.ParamsException;
import com.datao.bigidea.utils.contentextractor.ContentExtractor;
import com.datao.bigidea.utils.contentextractor.News;
import com.datao.bigidea.serviceImpl.service.ApiService;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

        result.put("url", news.getUrl());
        result.put("content", news.getContent());
        result.put("contentElement", news.getContentElement().toString());
        result.put("time", news.getTime());
        result.put("title", news.getTitle());
        return result;
    }

    /**
     * 提取文本中的数字
     *
     * @param content     文本内容
     * @param minSize 提取规则:数字的最小长度
     * @param maxSize 提取规则:数字的最大长度
     * @return 结果集
     */
    @Override
    public List<String> getNum(String content, Integer minSize, Integer maxSize) {
        String regular = null;
        if (content == null) {
            throw new ParamsException("传入参数错误!");
        }
        if (minSize != null && maxSize != null) {
            if (minSize > maxSize) {
                throw new ParamsException("传入参数错误!");
            }
            regular = "\\d{" + minSize + "," + maxSize + "}";
        } else if (minSize == null && maxSize != null) {
            regular = "\\d{1," + maxSize + "}";
        } else if (minSize != null && maxSize == null) {
            regular = "\\d{" + minSize + ", }";
        } else if (minSize == null && maxSize == null) {
            regular = "\\d+";
        }

        List<String> result = new ArrayList<>();

        Pattern pattern = Pattern.compile(regular);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            result.add(matcher.group());
        }

        return result;
    }
}
