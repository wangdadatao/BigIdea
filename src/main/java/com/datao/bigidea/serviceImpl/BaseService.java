package com.datao.bigidea.serviceImpl;

import com.datao.bigidea.exception.ParamsException;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

public class BaseService {

    @Resource
    public EhCacheCacheManager cacheManager;

    /**
     * 检查传入的参数
     *
     * @param object 传入的参数
     */
    public void captchaParams(Object ...object){
        for(Object o : object){
            if(ObjectUtils.isEmpty(o)){
                throw new ParamsException("传入参数错误!");
            }
        }
    }

}
