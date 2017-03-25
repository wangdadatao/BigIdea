package com.datao.bigidea.controller;

import com.datao.bigidea.entity.Blog;
import com.datao.bigidea.serviceImpl.service.BlogService;
import com.datao.bigidea.utils.ResEnv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by 王 海涛 on 2016/11/25.
 */
@Controller
@RequestMapping("/blog")
public class BlogController {

    Logger logger = LoggerFactory.getLogger(BlogController.class);

    @Resource
    private BlogService blogService;

    /**
     * 查询文章分类
     *
     * @return
     */
    @RequestMapping("/queryTypes")
    @ResponseBody
    public ResEnv<List<Map<String, String>>> queryTypes() {
        try {
            List<Map<String, String>> result = blogService.queryTypes();
            return ResEnv.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询博客列表时失败! /blog/write/queryTypes");
            return ResEnv.fail(e);
        }
    }

    /**
     * 根据ID查询文章
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/queryByID")
    @ResponseBody
    public ResEnv<Blog> queryByID(Integer id) {

        try {
            Blog result = blogService.queryByID(id);
            return ResEnv.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("根据ID查询博客失败! /blog/queryByID" + e);
            return ResEnv.fail(e);
        }

    }

    /**
     * 根据type查询文章
     *
     * @param type
     * @return
     */
    @RequestMapping(value = "/queryByType")
    @ResponseBody
    public ResEnv<List<Blog>> queryByType(String type, Integer pageNum, Integer pageSize) {

        try {
            List<Blog> result = blogService.queryByType(type, pageNum, pageSize);
            return ResEnv.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("根据type查询文章时失败! queryByType" + e);
            return ResEnv.fail(e);
        }

    }


    /**
     * 查询文章列表
     *
     * @param keyWords 关键词
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return
     */
    @RequestMapping(value = "/queryBlogList")
    @ResponseBody
    public ResEnv<List<Blog>> queryBlogList(String keyWords, Integer pageNum, Integer pageSize) {
        try {
            List<Blog> result = blogService.queryBlogList(keyWords, pageNum, pageSize);
            return ResEnv.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询文章列表时出错! ");
            return ResEnv.fail(e);
        }
    }


    /**
     * 添加博客
     *
     * @param blog 文章对象
     * @return
     */
    @RequestMapping(value = "/addBlog", method = RequestMethod.POST)
    @ResponseBody
    public ResEnv<Map<String, String>> addBlog(Blog blog) {
        try {
            Map<String, String> result = blogService.addBlog(blog);
            return ResEnv.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加博客时出错! addBlog" + e);
            return ResEnv.fail(e);
        }
    }

    /**
     * 更新博客
     *
     * @param blog 博客对象
     * @return 更新结果
     */
    @RequestMapping("updateBlog")
    @ResponseBody
    public ResEnv<Map<String, String>> updateBlog(Blog blog) {
        try {
            Map<String, String> result = blogService.updateBlog(blog);
            return ResEnv.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("跟新博客时出错！" + e);
            return ResEnv.fail(e);
        }
    }

}
