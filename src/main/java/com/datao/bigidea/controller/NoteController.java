package com.datao.bigidea.controller;

import com.datao.bigidea.entity.Note;
import com.datao.bigidea.serviceImpl.service.NoteService;
import com.datao.bigidea.utils.ResEnv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @RequestMapping("/queryTypes")
    @ResponseBody
    public ResEnv<List<Map<String, String>>> queryTypes() {
        try {
            List<Map<String, String>> result = noteService.queryTypes();
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
    @RequestMapping(value = "/queryByID")
    @ResponseBody
    public ResEnv<Note> queryByID(Integer id) {

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


    /**
     * 添加笔记
     *
     * @param note 文章对象
     * @return
     */
    @RequestMapping(value = "/addNote", method = RequestMethod.POST)
    @ResponseBody
    public ResEnv<Map<String, String>> addNote(Note note) {
        try {
            Map<String, String> result = noteService.addNote(note);
            return ResEnv.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加笔记时出错! addNote" + e);
            return ResEnv.fail(e);
        }
    }

    /**
     * 更新笔记
     *
     * @param note 笔记对象
     * @return 更新结果
     */
    @RequestMapping("updateNote")
    @ResponseBody
    public ResEnv<Map<String, String>> updateNote(Note note) {
        try {
            Map<String, String> result = noteService.updateNote(note);
            return ResEnv.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("跟新笔记时出错！" + e);
            return ResEnv.fail(e);
        }
    }

}
