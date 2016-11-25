package com.datao.bigidea.mapper;

import com.datao.bigidea.entity.Note;

import java.util.List;

/**
 * Created by 王 海涛 on 2016/11/25.
 */
public interface NoteMapper {

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
