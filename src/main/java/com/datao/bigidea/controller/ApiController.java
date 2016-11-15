package com.datao.bigidea.controller;

import com.datao.bigidea.utils.contentextractor.News;
import com.datao.bigidea.serviceImpl.service.ApiService;
import com.datao.bigidea.utils.ResEnv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
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
    public ResEnv<Map<String, String>> getUrlElement(String url) {
        try {
            Map<String, String> result = apiService.getUrlElement(url);
            return ResEnv.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("根据URL提取网页正文时失败!" + e);
            return ResEnv.fail(e);
        }
    }

    /**
     * 提取文章中的数字
     *
     * @param content     文本
     * @param minSize 提取规则: 数字的最小长度
     * @param maxSize 提取规则: 数字的最大长度
     * @return
     */
    @RequestMapping(value = "/math/getNum", method = RequestMethod.POST)
    @ResponseBody
    public ResEnv<List<String>> getNum(@RequestBody String content, Integer minSize, Integer maxSize) {
        try {
            List<String> result = apiService.getNum(content, minSize, maxSize);
            return ResEnv.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("根据URL提取网页正文时失败!" + e);
            return ResEnv.fail(e);
        }
    }


}
