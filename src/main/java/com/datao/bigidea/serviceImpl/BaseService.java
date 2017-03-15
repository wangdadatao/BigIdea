package com.datao.bigidea.serviceImpl;

import com.datao.bigidea.exception.ParamsException;
import com.datao.bigidea.utils.ConfigProp;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
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
    public void captchaParams(Object... object) {
        for (Object o : object) {
            if (ObjectUtils.isEmpty(o)) {
                throw new ParamsException("传入参数错误!");
            }
        }
    }

    /**
     * @param pageNum 页码
     * @return
     */
    public Integer getPageNum(Integer pageNum) {
        if (pageNum == null || pageNum == 0) {
            return 1;
        }
        return pageNum;
    }

    /**
     * 验证页码
     *
     * @param pageSize  每页数量
     * @return  每页数量
     */
    public Integer getPageSize(Integer pageSize) {
        if (pageSize == null || pageSize == 0) {
            return 20;
        }

        Integer maxPageSize = Integer.valueOf(ConfigProp.get("MAXPAGESIZE"));

        if(pageSize > maxPageSize){
            pageSize = maxPageSize;
        }
        return pageSize;
    }

    public String captchaUrl(String url) {
        String regex = "^(?:https?://)?[\\w]{1,}(?:\\.?[\\w]{1,})+[\\w-_/?&=#%:]*$";
        if (StringUtils.isEmpty(url)) {
            throw new ParamsException("请传入参数！");
        } else if (!url.matches(regex)) {
            throw new ParamsException("请传入正确格式的参数！");
        }
        return url;
    }

    /**
     * 得到当前时间
     *
     * @return 时间
     */
    public String getNowTime(){
        return new DateTime().toString("yyyy-MM-dd HH:mm:ss");
    }
}
