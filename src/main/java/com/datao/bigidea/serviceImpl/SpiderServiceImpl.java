package com.datao.bigidea.serviceImpl;

import com.datao.bigidea.entity.News;
import com.datao.bigidea.mapper.NewsMapper;
import com.datao.bigidea.serviceImpl.service.SpiderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 王 海涛 on 2016/11/22.
 */
@Service
public class SpiderServiceImpl implements SpiderService {

    @Resource
    private NewsMapper newsMapper;

    public void insert(News news) {
        newsMapper.insert(news);
    }

    @Override
    public List<News> queryAllNews() {
        return newsMapper.queryAllNews();
    }

    @Override
    public void update(News n) {
        newsMapper.update(n);
    }



}
