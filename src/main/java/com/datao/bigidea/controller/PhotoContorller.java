package com.datao.bigidea.controller;

import com.datao.bigidea.entity.BingImg;
import com.datao.bigidea.serviceImpl.service.PhotoService;
import com.datao.bigidea.utils.ResEnv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by 王 海涛 on 2017/4/1.
 * <p>
 * 图片
 */
@Controller
@RequestMapping("/photo")
public class PhotoContorller {

    private Logger logger = LoggerFactory.getLogger(PhotoContorller.class);

    @Resource
    private PhotoService photoService;

    @RequestMapping("getIndexImg")
    @ResponseBody
    public ResEnv<BingImg> getIndexImg() {
        try {
            BingImg result = photoService.getIndexImg();
            return ResEnv.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取首页图片失败！");
            return ResEnv.fail(e);
        }
    }


}
