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
     * 查询笔记分类
     *
     * @return 笔记分类
     */
    List<Map<String, String>> queryTypes();

    /**
     * 根据ID查询笔记
     *
     * @param id 笔记ID
     * @return 笔记对象
     */
    Note queryByID(Integer id);


    /**
     * 根据类别查询文章
     *
     * @param type 查询类型
     * @return 结果集
     */
    List<Note> queryByType(String type);

    /**
     * 查询文章列表
     *
     * @param keyWords 搜索关键词
     * @return 结果集
     */
    List<Note> queryNoteList(@Param("keyWords") String keyWords);

    /**
     * 添加note
     *
     * @param note 笔记对象
     */
    void insertNote(Note note);

    /**
     * 更新笔记内容
     *
     * @param note 笔记对象
     */
    void updateNote(Note note);
}
