package com.datao.bigidea.system.shiro;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CaptchaAutoShiroFilter extends AuthorizationFilter {


    private String callback;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    private EhCacheCacheManager cacheManager;

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    /**
     * 验证是否登陆
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        return true;
    }


    /**
     * 返回JSON
     *
     * @param request
     * @param response
     * @param obj
     * @throws IOException
     */
    private void returnJson(HttpServletRequest request, ServletResponse response, Object obj) throws IOException {
        if (obj == null)
            return;

        String callback = request.getParameter(
                StringUtils.defaultIfBlank(getCallback(), "callback"));

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setContentType("application/json; charset=UTF-8");

        String json = objectMapper.writeValueAsString(obj);
        if (StringUtils.isNotBlank(callback)) {
            json = callback + "(" + json + ")";
        }
        httpServletResponse.getWriter().write(json);
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

}