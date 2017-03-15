package com.datao.bigidea.mapper;

import com.datao.bigidea.entity.Note;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by 王 海涛 on 2016/11/25.
 */
public interface NoteMapper {

    /**
     * 查询博客分类
     *
     * @return
     */
    List<Map<String,String>> queryTypes();

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
     * @return
     */
    List<Note> queryByType(String type);

    /**
     * 查询文章列表
     *
     * @param keyWords 搜索关键词
     * @return
     */
    List<Note> queryNoteList(@Param("keyWords") String keyWords);

    /**
     * 添加note
     * @param note
     */
    int insertNote(Note note);
}
