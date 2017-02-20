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
import java.util.Map;

/**
 * Created by 王 海涛 on 2016/11/25.
 */
@Controller
@RequestMapping("/note")
public class NoteController {

    Logger logger = LoggerFactory.getLogger(NoteController.class);

    @Resource
    private NoteService noteService;

    /**
     * 查询文章分类
     *
     * @return
     */
    @RequestMapping("/write/queryTypes")
    @ResponseBody
    public ResEnv<List<Map<String,String>>> queryTypes() {
        try {
            List<Map<String,String>> result = noteService.queryTypes();
            return ResEnv.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询博客列表时失败! /note/write/queryTypes");
            return ResEnv.fail(e);
        }
    }

    /**
     * 根据ID查询文章
     *
     * @param id
     * @return
     */
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

    /**
     * 根据type查询文章
     *
     * @param type
     * @return
     */
    @RequestMapping(value = "/queryByType")
    @ResponseBody
    public ResEnv<List<Note>> queryByType(String type, Integer pageNum, Integer pageSize) {

        try {
            List<Note> result = noteService.queryByType(type, pageNum, pageSize);
            return ResEnv.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("根据type查询文章时失败! queryByType" + e);
            return ResEnv.fail(e);
        }

    }


    /**
     * 查询文章列表
     *
     * @param keyWords 关键词
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return
     */
    @RequestMapping(value = "/queryNoteList")
    @ResponseBody
    public ResEnv<List<Note>> queryNoteList(String keyWords, Integer pageNum, Integer pageSize) {
        try {
            List<Note> result = noteService.queryNoteList(keyWords, pageNum, pageSize);
            return ResEnv.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询文章列表时出错! ");
            return ResEnv.fail(e);
        }
    }
}
