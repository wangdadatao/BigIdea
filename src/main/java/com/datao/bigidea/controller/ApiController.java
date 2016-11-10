package com.datao.bigidea.controller;

import com.datao.bigidea.utils.contentextractor.News;
import com.datao.bigidea.serviceImpl.service.ApiService;
import com.datao.bigidea.utils.ResEnv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;


@Controller
@RequestMapping("api")
public class ApiController {

    Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Resource
    private ApiService apiService;

    /**
     * 根据URL提取网页正文
     *
     * @param url 网址
     * @return
     */
    @RequestMapping("/url/getUrlElement")
    @ResponseBody
    public ResEnv<Map<String,String>> getUrlElement(String url) {
        try {
            Map<String,String> result = apiService.getUrlElement(url);
            return ResEnv.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("根据URL提取网页正文时失败!" + e);
            return null;
        }
    }


}
