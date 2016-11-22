package com.datao.bigidea.serviceImpl.service;

import com.datao.bigidea.entity.News;

import java.util.List;

/**
 * Created by 王 海涛 on 2016/11/22.
 */
public interface SpiderService {

    void insert(News news);

    /**
     * 查询所有的News
     *
     * @return
     */
    List<News> queryAllNews();

    /**
     * 修改News
     *
     * @param n
     */
    void update(News n);
}
