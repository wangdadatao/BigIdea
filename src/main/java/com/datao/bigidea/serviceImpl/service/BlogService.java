package com.datao.bigidea.serviceImpl.service;

import com.datao.bigidea.entity.Blog;

import java.util.List;
import java.util.Map;

/**
 * Created by 王 海涛 on 2016/11/25.
 */
public interface BlogService {

    /**
     * 查询博客分类
     *
     * @return
     */
    List<Map<String, String>> queryTypes();

    /**
     * 根据ID查询博客
     *
     * @param id
     * @return
     */
    Blog queryByID(Integer id);

    /**
     * 根据类别查询文章
     *
     * @param type
     * @param pageNum
     * @param pageSize @return
     */
    List<Blog> queryByType(String type, Integer pageNum, Integer pageSize);

    /**
     * 查询文章列表
     *
     * @param keyWords
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Blog> queryBlogList(String keyWords, Integer pageNum, Integer pageSize);

    /**
     * 添加博客
     *
     * @param blog 博客对象
     * @return 添加结果
     */
    Map<String, String> addBlog(Blog blog);

    /**
     * 跟新博客内容
     *
     * @param blog 博客对象
     * @return 更新结果
     */
    Map<String, String> updateBlog(Blog blog);
}
