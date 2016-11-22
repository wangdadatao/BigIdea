package com.datao.bigidea.mapper;

import com.datao.bigidea.entity.News;

import java.util.List;

/**
 * Created by 王 海涛 on 2016/11/22.
 */
public interface NewsMapper {

    /**
     * 插入新的News
     *
     * @param news
     */
    void insert(News news);

    /**
     * 查询所有的news
     *
     * @return
     */
    List<News> queryAllNews();

    /**
     * 更新News信息
     *
     * @param n
     */
    void update(News n);
}
