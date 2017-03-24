package com.datao.bigidea.serviceImpl;

import com.datao.bigidea.entity.Note;
import com.datao.bigidea.mapper.NoteMapper;
import com.datao.bigidea.serviceImpl.service.NoteService;
import com.datao.bigidea.system.CtxUtils;
import com.datao.bigidea.utils.contentextractor.ContentExtractor;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by 王 海涛 on 2016/11/25.
 * <p>
 * 笔记service
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
    public List<Map<String, String>> queryTypes() {
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
        Note note = noteMapper.queryByID(id);

        note.setClickNum(note.getClickNum() + 1);
        noteMapper.updateNote(note);

        return note;
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
     * @param pageNum  页码
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

    /**
     * 添加笔记
     *
     * @param note 笔记对象
     * @return 添加结果
     */
    @Override
    public Map<String, String> addNote(Note note) {
        captchaParams(note);

        captchaParams(note.getTitle());
        note.setCreateTime(getNowTime());
        note.setCreateIP(CtxUtils.getIpAddress());
        if (note.getType() == null) {
            note.setType("暂无分类");
        }
        note.setStatus(1);
        note.setClickNum(0);
        note.setReplyNum(0);

        Document coc = Jsoup.parse("<body id='insertBody'>" + note.getContent() + "</body>");
        Element element = coc.getElementById("insertBody");
        note.setContentNoElement(element.text());

        noteMapper.insertNote(note);
        Map<String, String> result = Maps.newHashMap();
        result.put("status", "true");
        result.put("msg", "添加成功!");
        result.put("id", String.valueOf(note.getId()));
        return result;
    }

    /**
     * 跟新笔记内容
     *
     * @param note 笔记对象
     * @return 更新结果
     */
    @Override
    public Map<String, String> updateNote(Note note) {
        captchaParams(note);
        captchaParams(note.getId(), note.getTitle());

        note.setLastChangeIP(CtxUtils.getIpAddress());
        note.setLastChangeTime(getNowTime());

        noteMapper.updateNote(note);
        Map<String, String> result = Maps.newHashMap();
        result.put("status", "true");
        result.put("msg", "修改成功");
        return result;
    }
}
