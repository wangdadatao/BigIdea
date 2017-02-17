package com.datao.bigidea.serviceImpl;

import com.datao.bigidea.entity.Note;
import com.datao.bigidea.mapper.NoteMapper;
import com.datao.bigidea.serviceImpl.service.NoteService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 王 海涛 on 2016/11/25.
 */
@Service
public class NoteServiceImpl extends BaseService implements NoteService {

    @Resource
    private NoteMapper noteMapper;


    /**
     * 查询博客分类
     *
     * @return
     */
    @Override
    public List<String> queryTypes() {
        return noteMapper.queryTypes();
    }

    /**
     * 根据ID查询博客
     *
     * @param id
     * @return
     */
    @Override
    public Note queryByID(Integer id) {
        return noteMapper.queryByID(id);
    }

    /**
     * @param type     类别
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return
     */
    @Override
    public List<Note> queryByType(String type, Integer pageNum, Integer pageSize) {
        captchaParams(type);
        pageNum = getPageNum(pageNum);
        pageSize = getPageSize(pageSize);

        PageHelper.startPage(pageNum, pageSize);
        return noteMapper.queryByType(type);
    }

    /**
     * 查询文章列表
     *
     * @param keyWords 关键词搜索
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return
     */
    @Override
    public List<Note> queryNoteList(String keyWords, Integer pageNum, Integer pageSize) {
        pageNum = getPageNum(pageNum);
        pageSize = getPageSize(pageSize);

        PageHelper.startPage(pageNum, pageSize);
        return noteMapper.queryNoteList(keyWords);
    }
}
