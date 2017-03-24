package com.datao.bigidea.serviceImpl.service;

import com.datao.bigidea.entity.Extract;

import java.util.Map;

/**
 * Created by 王 海涛 on 2017/3/24.
 */
public interface ExtractService {

    /**
     * 添加新的摘录
     *
     * @param url 目标网址
     * @return 添加结果
     */
    Map<String, String> addExtract(String url);
}
