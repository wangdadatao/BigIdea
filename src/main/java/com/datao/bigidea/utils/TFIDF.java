package com.datao.bigidea.utils;

import com.datao.bigidea.entity.News;
import com.datao.bigidea.entity.WordsTFIDF;
import com.datao.bigidea.mapper.NewsMapper;
import com.datao.bigidea.serviceImpl.SpiderServiceImpl;
import com.google.common.collect.Maps;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;

/**
 * Created by 王 海涛 on 2016/11/25.
 */
@Service
public class TFIDF {

    @Resource
    private SpiderServiceImpl spiderService;

    private List<News> newsList;

    private Set<String> IDs;

    private Map<String, Map<String, Integer>> baseNum;

    private Map<String, Map<String, Double>> TF;

    private Map<String, Map<String, Double>> IDF;

    private Map<String, Map<String, Double>> TI;

    private Map<String, Integer> articleNum;

    private Map<String, Integer> allNum;


    public void Innit(List<News> newsList){
        this.newsList = newsList;

        this.baseNum = calculateBaseNum();
        this.IDs = calculateIDs();
        this.articleNum = getArticleNum();
        this.allNum = getAllNum();

        this.TF = calculateTF();
        this.IDF = calculateIDF();
        this.TI = calculateTI();
    }


    /**
     * 提取出所有的文章ID
     */
    private Set<String> calculateIDs() {
        Set<String> ids = baseNum.keySet();
        return ids;
    }

    private Map<String, Map<String, Integer>> calculateBaseNum() {
        Map<String, Map<String, Integer>> fre = Maps.newHashMap();

        for (News n : newsList) {
            fre.put(n.getId().toString(), strNum(n.getContent()));
        }

        return fre;
    }

    public static Map<String, Integer> strNum(String str) {
        Map<String, Integer> result = Maps.newHashMap();

        Reader input = new StringReader(str);
        IKSegmenter iks = new IKSegmenter(input, true);
        Lexeme lexeme = null;
        try {
            while ((lexeme = iks.next()) != null) {
                if (result.containsKey(lexeme.getLexemeText())) {
                    result.put(lexeme.getLexemeText(), result.get(lexeme.getLexemeText()) + 1);
                } else {
                    result.put(lexeme.getLexemeText(), 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }


    /**
     * 得到 TF 词频
     *
     * @return
     */
    public Map<String, Map<String, Double>> calculateTF() {
        Map<String, Map<String, Double>> result = Maps.newHashMap();

        for (String s : IDs) {
            Map<String, Integer> bNum = baseNum.get(s);
            Map<String, Double> tf = Maps.newHashMap();


            Integer aNewsWords = 0; //统计文章的总次数
            Set<String> wordList = bNum.keySet();
            for (String t : wordList) {
                aNewsWords += bNum.get(t);
            }

            for (String t : wordList) {
                tf.put(t, Double.valueOf(bNum.get(t)) / Double.valueOf(aNewsWords));
            }

            result.put(s, tf);
        }

        return result;
    }

    /**
     * 计算 IDF
     *
     * @return
     */
    public Map<String, Map<String, Double>> calculateIDF() {
        Map<String, Map<String, Double>> result = Maps.newHashMap();
        Map<String, Integer> articleNum = getArticleNum();

        Integer num = baseNum.size();

        for (String s : IDs) {
            Map<String, Double> IDF = Maps.newHashMap();

            Map<String, Integer> bNum = baseNum.get(s);
            Set<String> aNewsWords = bNum.keySet();
            for (String t : aNewsWords) {
                IDF.put(t, Math.log(num) / Math.log(articleNum.get(t) + 1));
            }
            result.put(s, IDF);
        }
        return result;
    }


    /**
     * 计算TF_IDF
     *
     * @return
     */
    public Map<String, Map<String, Double>> calculateTI() {

        Map<String, Map<String, Double>> result = Maps.newHashMap();

        for (String s : IDs) {
            Map<String, Double> TFIDF = Maps.newHashMap();

            Map<String, Double> tf = TF.get(s);
            Map<String, Double> idf = IDF.get(s);

            Set<String> aNewsWords = tf.keySet();
            for (String t : aNewsWords) {
                TFIDF.put(t, tf.get(t) * idf.get(t));
            }
            result.put(s, TFIDF);
        }

        return result;
    }


    /**
     * 统计所有的词出现过的文章个数
     *
     * @return
     */
    private Map<String, Integer> getArticleNum() {
        Map<String, Integer> result = Maps.newHashMap();
        Set<String> IDs = baseNum.keySet();

        for (String s : IDs) {
            Map<String, Integer> bNum = baseNum.get(s);

            Set<String> aNewsWords = bNum.keySet();
            for (String t : aNewsWords) {
                if (result.containsKey(t)) {
                    result.put(t, result.get(t) + 1);
                } else {
                    result.put(t, 1);
                }
            }
        }

        return result;
    }

    /**
     * 统计所有的词出现的在所有的文章中出现的总次数
     *
     * @return
     */
    private Map<String, Integer> getAllNum() {

        Map<String, Integer> result = Maps.newHashMap();

        for (String s : IDs) {
            Map<String, Integer> bNum = baseNum.get(s);

            Set<String> aNewsWords = bNum.keySet();
            for (String t : aNewsWords) {
                if (result.containsKey(t)) {
                    result.put(t, result.get(t) + bNum.get(t));
                } else {
                    result.put(t, bNum.get(t));
                }
            }
        }

        return result;
    }


    /**
     * 保存TFIDF
     */
    public void saveTFIDF() {

        for (String s : IDs) {
            Map<String, Integer> fr = baseNum.get(s);
            Map<String, Double> ti = TF.get(s);
            Map<String, Double> tf = TF.get(s);
            Map<String, Double> idf = IDF.get(s);

            Set<String> tWord = fr.keySet();

            List<WordsTFIDF> words = new ArrayList<>();

            Integer i = 0;
            for (String t : tWord) {
                WordsTFIDF word = new WordsTFIDF();

                word.setBlogID(Integer.valueOf(s));
                word.setWords(t);
                word.setTF(tf.get(t));
                word.setIDF(idf.get(t));
                word.setTFIDF(ti.get(t));
                word.setThisNum(fr.get(t));
                word.setArticleNum(articleNum.get(t));
                word.setAllNum(allNum.get(t));

                words.add(word);
                i++;
            }
            spiderService.insertWords(words);
        }

    }
}
