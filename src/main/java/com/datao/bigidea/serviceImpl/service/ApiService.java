package com.datao.bigidea.serviceImpl.service;

import com.datao.bigidea.utils.contentextractor.News;

import java.util.List;
import java.util.Map;


public interface ApiService {

    /**
     * 根据URL 获取网页正文
     *
     * @param url
     * @return
     */
    Map<String, String> getUrlElement(String url) throws Exception;

    /**
     * 提取文本中的数字
     *
     * @param content     文本内容
     * @param minSize 提取规则:数字的最小长度
     * @param maxSize 提取规则:数字的最大长度
     * @return 结果
     */
    List<String> getNum(String content, Integer minSize, Integer maxSize);

    /**
     * 对比两个字符串的相似度
     *
     * @param text1 文本1
     * @param text2 文本2
     * @return
     */
    Map<String,String> getSimilarity(String text1, String text2);
}
