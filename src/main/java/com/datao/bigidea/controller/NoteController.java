package com.datao.bigidea.controller;

import com.datao.bigidea.entity.Note;
import com.datao.bigidea.serviceImpl.service.NoteService;
import com.datao.bigidea.utils.ResEnv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 王 海涛 on 2016/11/25.
 */
@Controller
@RequestMapping("/note")
public class NoteController {

    Logger logger = LoggerFactory.getLogger(NoteController.class);

    @Resource
    private NoteService noteService;

    @RequestMapping("/write/queryTypes")
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

    @RequestMapping(value = "/queryByID/{id:^\\d+$}")
    @ResponseBody
    public ResEnv<Note> queryByID(@PathVariable Integer id) {

        try {
            Note result = noteService.queryByID(id);
            return ResEnv.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("根据ID查询博客失败! /note/queryByID" + e);
            return ResEnv.fail(e);
        }

    }
}
