package com.datao.bigidea.controller;

import com.datao.bigidea.entity.BingImg;
import com.datao.bigidea.serviceImpl.service.PhotoService;
import com.datao.bigidea.system.CtxUtils;
import com.datao.bigidea.utils.ConfigProp;
import com.datao.bigidea.utils.ResEnv;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

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

    /**
     * 查询首页图片
     *
     * @return 图片对象
     */
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

    @RequestMapping("/showPhoto/{photo:^\\w*-\\w*-\\w*-\\w*-\\w*.\\w*$}")
    @ResponseBody
    public ResEnv<String> showPhoto(HttpServletResponse response, @PathVariable String photo, Integer width, Integer height) {
        try {
            File file = new File(ConfigProp.get("PHOTOPATH"), photo);


            response.setContentType("image/png");
            //建立IO流
            FileInputStream fileInputStream = new FileInputStream(file);
            OutputStream outputStream = response.getOutputStream();


            IOUtils.copy(fileInputStream, outputStream);

            outputStream.flush();
            outputStream.close();
            fileInputStream.close();
            return ResEnv.success("true");
        } catch (Exception e) {
            return ResEnv.fail("false");
        }

    }


}
