package com.datao.bigidea.serviceImpl;

import com.datao.bigidea.exception.ParamsException;
import com.datao.bigidea.utils.Similarity;
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
     * @param content 文本内容
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

    /**
     * 两个字符串的相似度
     *
     * @param text1 文本1
     * @param text2 文本2
     * @return 结果集
     */
    @Override
    public Map<String, String> getSimilarity(String text1, String text2) {
        Map<String, String> result = Maps.newHashMap();

        if (text1.length() > 2000 || text2.length() > 2000) {
            result.put("status", "false");
            result.put("msg", "本文过大!对比文本不能超过2000字符");
            return result;
        }
        result.put("LevenshteinDistance", String.valueOf(Similarity.LDistance(text1, text2)));
        result.put("Cosine", String.valueOf(Similarity.getConSimilarity(text1, text2)));

        return result;

    }
}
