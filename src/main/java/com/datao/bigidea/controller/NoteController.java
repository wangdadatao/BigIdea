package com.datao.bigidea.controller;

import com.datao.bigidea.serviceImpl.service.NoteService;
import com.datao.bigidea.utils.ResEnv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 王 海涛 on 2016/11/25.
 */
@Controller
@RequestMapping("/note/write")
public class NoteController {

    Logger logger = LoggerFactory.getLogger(NoteController.class);

    @Resource
    private NoteService noteService;

    @RequestMapping("/queryTypes")
    @ResponseBody
    public ResEnv<List<String>> queryTypes() {
        try {
            List<String> result = noteService.queryTypes();
            return ResEnv.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询博客列表时失败! /note/write/queryTypes");
            return ResEnv.fail(e);
        }
    }
}
