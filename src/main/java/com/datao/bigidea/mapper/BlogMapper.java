package com.datao.bigidea.mapper;

import com.datao.bigidea.entity.Blog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by 王 海涛 on 2016/11/25.
 */
public interface BlogMapper {

    /**
     * 查询博客分类
     *
     * @return 博客分类
     */
    List<Map<String, String>> queryTypes();

    /**
     * 根据ID查询博客
     *
     * @param id 博客ID
     * @return 博客对象
     */
    Blog queryByID(Integer id);


    /**
     * 根据类别查询文章
     *
     * @param type 查询类型
     * @return 结果集
     */
    List<Blog> queryByType(String type);

    /**
     * 查询文章列表
     *
     * @param keyWords 搜索关键词
     * @return 结果集
     */
    List<Blog> queryBlogList(@Param("keyWords") String keyWords);

    /**
     * 添加blog
     *
     * @param blog 博客对象
     */
    void insertBlog(Blog blog);

    /**
     * 更新博客内容
     *
     * @param blog 博客对象
     */
    void updateBlog(Blog blog);
}
