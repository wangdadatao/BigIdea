package com.datao.bigidea.test;

import com.datao.bigidea.entity.WordsTFIDF;
import com.datao.bigidea.mapper.NewsMapper;
import com.datao.bigidea.serviceImpl.SpiderServiceImpl;
import com.datao.bigidea.serviceImpl.service.SpiderService;
import com.datao.bigidea.utils.HttpUtil;
import org.junit.Test;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 王 海涛 on 2016/11/25.
 */
public class MyThread extends Thread {

    @Resource
    private SpiderService spiderService;

    private String s;

    private Map<String, Integer> fr;

    private Map<String, Double> ti;

    private Map<String, Double> ft;

    private Map<String, Double> fd;

    private Map<String, Map<String, Integer>> fre;

    private Set<String> titles;


    public MyThread(String s,
                    Map<String, Integer> fr,
                    Map<String, Double> ti,
                    Map<String, Double> ft,
                    Map<String, Double> fd,
                    Map<String, Map<String, Integer>> fre) {

        System.out.println("进入了多线程....");

        this.s = s;
        this.fr = fr;
        this.ti = ti;
        this.ft = ft;
        this.fd = fd;
        this.fre = fre;
        this.titles = fre.keySet();
    }

    public void run() {
        System.out.println("运行了Run方法...");
        List<WordsTFIDF> words = new ArrayList<>();

        Set<String> tWord = fr.keySet();
        for (String t : tWord) {
            WordsTFIDF word = new WordsTFIDF();

            word.setBlogID(Integer.valueOf(s));
            word.setWords(t);
            word.setTF(ft.get(t));
            word.setIDF(fd.get(t));
            word.setTFIDF(ti.get(t));
            word.setThisNum(fr.get(t));

            for (String r : titles) {
                Integer articleNum = 0;
                for (String n : titles) {
                    Map<String, Integer> articles = fre.get(n);
                    if (articles.containsKey(t)) {
                        articleNum++;
                    }
                }
                word.setArticleNum(articleNum);

                Integer allNum = 0;
                for (String n : titles) {
                    Map<String, Integer> articles = fre.get(n);
                    if (articles.containsKey(t)) {
                        allNum += articles.get(t);
                    }
                }
                word.setAllNum(allNum);
            }
            words.add(word);
        }
        spiderService.insertWords(words);
    }




}
