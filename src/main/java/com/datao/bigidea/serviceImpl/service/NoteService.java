package com.datao.bigidea.serviceImpl.service;

import com.datao.bigidea.entity.Note;

import java.util.List;
import java.util.Map;

/**
 * Created by 王 海涛 on 2016/11/25.
 */
public interface NoteService {

    /**
     * 查询博客分类
     *
     * @return
     */
    List<Map<String, String>> queryTypes();

    /**
     * 根据ID查询博客
     *
     * @param id
     * @return
     */
    Note queryByID(Integer id);

    /**
     * 根据类别查询文章
     *
     * @param type
     * @param pageNum
     * @param pageSize @return
     */
    List<Note> queryByType(String type, Integer pageNum, Integer pageSize);

    /**
     * 查询文章列表
     *
     * @param keyWords
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Note> queryNoteList(String keyWords, Integer pageNum, Integer pageSize);

    /**
     * 添加笔记
     *
     * @param note 笔记对象
     * @return 添加结果
     */
    Map<String, String> addNote(Note note);

    /**
     * 跟新笔记内容
     *
     * @param note 笔记对象
     * @return 更新结果
     */
    Map<String, String> updateNote(Note note);
}
