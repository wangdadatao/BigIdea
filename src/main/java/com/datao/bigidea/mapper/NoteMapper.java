package com.datao.bigidea.mapper;

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
}
