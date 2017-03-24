package com.datao.bigidea.serviceImpl;

import com.datao.bigidea.entity.Extract;
import com.datao.bigidea.entity.News;
import com.datao.bigidea.mapper.ExtractMapper;
import com.datao.bigidea.serviceImpl.service.ExtractService;
import com.datao.bigidea.utils.contentextractor.ContentExtractor;
import com.google.common.collect.Maps;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by 王 海涛 on 2017/3/24.
 * <p>
 * 网摘
 */
@Service
public class ExtractServiceImpl extends BaseService implements ExtractService {

    @Resource
    private ExtractMapper extractMapper;

    /**
     * 添加新的斋虑
     *
     * @param url 目标网址
     * @return 添加结果
     */
    @Override
    public Map<String, String> addExtract(String url) {
        captchaUrl(url);
        Map<String, String> result = Maps.newHashMap();

        News news = null;
        try {
            news = ContentExtractor.getNewsByUrl(url);
            Extract extract = new Extract();
            extract.setUrl(news.getUrl());
            extract.setTitle(news.getTitle());
            extract.setContent(news.getContentElement().toString());
            extract.setContentNoElement(news.getContent());
            extract.setPubTime(news.getTime());
            extract.setCreateTime(getNowTime());
            extract.setUrlMD5(DigestUtils.md5Hex(news.getUrl()));

            extractMapper.insertExtract(extract);

        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "false");
            result.put("msg", "解析URL失败！");
            return result;
        }

        result.put("status", "true");
        result.put("msg", "添加成功！");
        return result;
    }
}
