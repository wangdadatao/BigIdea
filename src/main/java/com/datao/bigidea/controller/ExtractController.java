package com.datao.bigidea.controller;

import com.datao.bigidea.entity.Extract;
import com.datao.bigidea.serviceImpl.service.ExtractService;
import com.datao.bigidea.utils.ResEnv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by 王 海涛 on 2017/3/24.
 * <p>
 * 摘录
 */
@Controller
@RequestMapping("extract")
public class ExtractController {

    private Logger logger = LoggerFactory.getLogger(ExtractController.class);

    @Resource
    private ExtractService extractService;

    /**
     * 添加新的摘录内容
     *
     * @param url 目标网址
     * @return 添加结果
     */
    @RequestMapping("addExtract")
    @ResponseBody
    public ResEnv<Map<String, String>> addExtract(String url) {
        try {
            Map<String, String> result = extractService.addExtract(url);
            return ResEnv.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加新的摘录内容 添加摘录内容是出错！addExtract" + e);
            return ResEnv.fail(e);
        }
    }


}
