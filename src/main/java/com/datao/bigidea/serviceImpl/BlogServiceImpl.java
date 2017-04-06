package com.datao.bigidea.serviceImpl;

import com.datao.bigidea.entity.Blog;
import com.datao.bigidea.mapper.BlogMapper;
import com.datao.bigidea.serviceImpl.service.BlogService;
import com.datao.bigidea.system.CtxUtils;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by 王 海涛 on 2016/11/25.
 * <p>
 * 博客service
 */
@Service
public class BlogServiceImpl extends BaseService implements BlogService {

    @Resource
    private BlogMapper blogMapper;


    /**
     * 查询博客分类
     *
     * @return
     */
    @Override
    public List<Map<String, String>> queryTypes() {
        return blogMapper.queryTypes();
    }

    /**
     * 根据ID查询博客
     *
     * @param id
     * @return
     */
    @Override
    public Blog queryByID(Integer id) {
        Blog blog = blogMapper.queryByID(id);

        Blog newBlog = new Blog();
        newBlog.setId(blog.getId());
        newBlog.setClickNum(blog.getClickNum() + 1);
        blogMapper.updateBlog(newBlog);

        return blog;
    }

    /**
     * @param type     类别
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return
     */
    @Override
    public List<Blog> queryByType(String type, Integer pageNum, Integer pageSize) {
        captchaParams(type);
        pageNum = getPageNum(pageNum);
        pageSize = getPageSize(pageSize);

        PageHelper.startPage(pageNum, pageSize);
        return blogMapper.queryByType(type);
    }

    /**
     * 查询文章列表
     *
     * @param keyWords 关键词搜索
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return
     */
    @Override
    public List<Blog> queryBlogList(String keyWords, Integer pageNum, Integer pageSize) {
        pageNum = getPageNum(pageNum);
        pageSize = getPageSize(pageSize);

        PageHelper.startPage(pageNum, pageSize);
        return blogMapper.queryBlogList(keyWords);
    }

    /**
     * 添加博客
     *
     * @param blog 博客对象
     * @return 添加结果
     */
    @Override
    public Map<String, String> addBlog(Blog blog) {
        captchaParams(blog);

        captchaParams(blog.getTitle());
        blog.setCreateTime(getNowTime());
        blog.setCreateIP(CtxUtils.getIpAddress());
        if (blog.getType() == null) {
            blog.setType("暂无分类");
        }
        blog.setStatus(1);
        blog.setClickNum(0);
        blog.setReplyNum(0);

        Document coc = Jsoup.parse("<body id='insertBody'>" + blog.getContent() + "</body>");
        Element element = coc.getElementById("insertBody");
        blog.setContentNoElement(element.text());

        blogMapper.insertBlog(blog);
        Map<String, String> result = Maps.newHashMap();
        result.put("status", "true");
        result.put("msg", "添加成功!");
        result.put("id", String.valueOf(blog.getId()));
        return result;
    }

    /**
     * 跟新博客内容
     *
     * @param blog 博客对象
     * @return 更新结果
     */
    @Override
    public Map<String, String> updateBlog(Blog blog) {
        captchaParams(blog);
        captchaParams(blog.getId(), blog.getTitle());

        blog.setLastChangeIP(CtxUtils.getIpAddress());
        blog.setLastChangeTime(getNowTime());

        blogMapper.updateBlog(blog);
        Map<String, String> result = Maps.newHashMap();
        result.put("status", "true");
        result.put("msg", "修改成功");
        return result;
    }
}
