package com.datao.bigidea.serviceImpl.service;

import com.datao.bigidea.entity.Note;

import java.util.List;

/**
 * Created by 王 海涛 on 2016/11/25.
 */
public interface NoteService {

    /**
     * 查询博客分类
     *
     * @return
     */
    List<String> queryTypes();

    /**
     * 根据ID查询博客
     *
     * @param id
     * @return
     */
    Note queryByID(Integer id);
}