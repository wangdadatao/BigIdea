package com.datao.bigidea.test;

import com.datao.bigidea.BaseTest;
import com.datao.bigidea.entity.News;
import com.datao.bigidea.entity.WordsTFIDF;
import com.datao.bigidea.mapper.NewsMapper;
import com.datao.bigidea.utils.Similarity;
import com.datao.bigidea.utils.TFIDF;
import com.google.common.collect.Maps;
import org.eclipse.jetty.util.ajax.JSON;
import org.junit.Test;
import org.junit.runner.Result;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;

/**
 * Created by 王 海涛 on 2016/11/22.
 */
public class SimilarityTest extends BaseTest {

    @Resource
    private NewsMapper newsMapper;

    @Test
    public void testSplit() {
        String str = "现在讲单词到了 这是个形容词 它是从什么动词来的 动词的变位如何 动词过去分词如何 副词是什么 名词是什么的地步。。。词汇量大爆炸一般砸来 你接得住就战斗力猛涨 接不住就歇菜吧连带质疑自己智商为负连带后悔当初选语言专业。。。";
        Reader input = new StringReader(str);
        // 智能分词关闭（对分词的精度影响很大）
        IKSegmenter iks = new IKSegmenter(input, true);
        Lexeme lexeme = null;
        Map<String, Integer> words = new LinkedHashMap<String, Integer>();
        try {
            while ((lexeme = iks.next()) != null) {
                System.out.println(lexeme.getLexemeText());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testSimilarity() {
        System.out.println(Similarity.getConSimilarity("我喜欢看电视，不喜欢看电影", "我不喜欢看电视，也不喜欢看电影。"));
        System.out.println(Similarity.LDistance("我喜欢看电视，不喜欢看电影", "我不喜欢看电视，也不喜欢看电影。"));
    }

    @Test
    public void testStr() {
        String str = "  abc  ddd 123";
        str = str.replace(" ", "");
        System.out.println(str);
    }


    @Test
    public void testKeyWord() {
        String str1 = "我喜欢看电视，不喜欢看电影"; //"你看起开很好吃吗,我要吃了你,哈哈";
        String str2 = "我不喜欢看电视，也不喜欢看电影。"; //"你看起开不好吃吗,我不要吃你,哼哼";

        Map<String, Integer> all = TFIDF.segStr(str1 + str2);
        Map<String, Integer> re1 = TFIDF.segStr(str1);
        Map<String, Integer> re2 = TFIDF.segStr(str2);

        List<Integer> first = new ArrayList<>();
        List<Integer> second = new ArrayList<>();

        Set<String> allKey = all.keySet();

        for (String s : allKey) {
            Integer num1 = re1.get(s);
            Integer num2 = re2.get(s);
            if (num1 == null) {
                num1 = 0;
            }
            if (num2 == null) {
                num2 = 0;
            }
            first.add(num1);
            second.add(num2);
        }

        Double sumUp = 0.0;
        Double reDown = null;
        Double le = 0.0;
        Double re = 0.0;

        for (int i = 0; i < first.size(); i++) {
            sumUp += first.get(i) * second.get(i);
            le += first.get(i) * first.get(i);
            re += second.get(i) * second.get(i);
        }

        reDown = Math.sqrt(le * re);

        System.out.println(JSON.toString(all));
        System.out.println(JSON.toString(re1));
        System.out.println(JSON.toString(re2));
        System.out.println(JSON.toString(first));
        System.out.println(JSON.toString(second));
        System.out.println(sumUp / reDown);
    }

    @Test
    public void testTFIDF() {
        List<News> newses = newsMapper.queryAllNews();

        Map<String, Map<String, Integer>> fre = Maps.newHashMap();
        for (News n : newses) {
            fre.put(n.getId().toString(), TFIDF.segStr(n.getContent()));
        }


        Map<String, Map<String, Double>> freTF = Maps.newHashMap();
        Map<String, Map<String, Double>> freIDF = Maps.newHashMap();
        Map<String, Map<String, Double>> TFIDF = Maps.newHashMap();


        freTF = getTF(fre);

        Integer articleNum = newses.size();
        freIDF = getIDF(fre, articleNum);

        TFIDF = getTFIDF(freTF, freIDF);

        saveTFIDF(fre, freTF, freIDF, TFIDF);




     /*   Map<String, Integer> allFre = Maps.newHashMap();
        Set<String> keyTitles = fre.keySet();




        for (String s : keyTitles) {
            Set<String> keyWords = fre.get(s).keySet();

            for (String k : keyWords) {
                if (allFre.containsKey(k)) {
                    allFre.put(k, allFre.get(k) + fre.get(s).get(k));
                } else {
                    allFre.put(k, fre.get(s).get(k));
                }
            }
        }

        Set<String> sh = allFre.keySet();
        for (String s : sh) {
            System.out.println(s + " -- " + allFre.get(s));
        }*/
    }

    private Map<String, List<WordsTFIDF>> getWords(Map<String, Map<String, Integer>> fre) {

        Map<String, List<WordsTFIDF>> result = Maps.newHashMap();

        Set<String> ids = fre.keySet();
        for (String s : ids) {
            List<WordsTFIDF> wordslist = new ArrayList<>();

            Map<String, Integer> fr = fre.get(s);
            Set<String> wordsl = fr.keySet();
            for (String t : wordsl) {

                WordsTFIDF w = new WordsTFIDF();
                w.setWords(t);
                w.setBlogID(Integer.valueOf(s));
                w.setThisNum(fr.get(t));

                for (String r : ids) {

                    Integer articleNum = 0;
                    for (String i : ids) {
                        Map<String, Integer> articles = fre.get(r);
                        if (articles.containsKey(t)) {
                            articleNum++;
                        }
                    }
                    w.setArticleNum(articleNum);

                    Integer allNum = 0;
                    for (String i : ids) {
                        Map<String, Integer> articles = fre.get(r);
                        if (articles.containsKey(t)) {
                            allNum += articles.get(t);
                        }
                    }
                    w.setAllNum(allNum);

                }
                wordslist.add(w);
            }
            result.put(s, wordslist);
        }
        return result;
    }


    /**
     * 得到 TF 词频
     *
     * @param fre
     * @return
     */
    public Map<String, Map<String, Double>> getTF(Map<String, Map<String, Integer>> fre) {
        Map<String, Map<String, Double>> result = Maps.newHashMap();

        Set<String> keys = fre.keySet();
        for (String s : keys) {
            Map<String, Integer> fr = fre.get(s);
            Integer allWords = 0;

            Map<String, Double> tf = Maps.newHashMap();

            Set<String> wordList = fr.keySet();
            for (String t : wordList) {
                allWords += fr.get(t);
            }

            for (String t : wordList) {
                tf.put(t, Double.valueOf(fr.get(t)) / Double.valueOf(allWords));
            }

            result.put(s, tf);
        }

        return result;
    }

    /**
     * 计算 IDF
     *
     * @param fre
     * @param num
     * @return
     */
    public Map<String, Map<String, Double>> getIDF(Map<String, Map<String, Integer>> fre, Integer num) {
        Map<String, Map<String, Double>> result = Maps.newHashMap();

        Set<String> keys = fre.keySet();
        for (String s : keys) {
            Map<String, Integer> article = fre.get(s);
            Set<String> artKeys = article.keySet();

            Map<String, Double> IDF = Maps.newHashMap();
            for (String t : artKeys) {

                Integer mark = 0;
                for (String r : keys) {
                    Map<String, Integer> articles = fre.get(r);
                    if (articles.containsKey(t)) {
                        mark++;
                    }
                }
                IDF.put(t, Math.log(num) / Math.log(mark + 1));
            }
            result.put(s, IDF);
        }
        return result;
    }


    /**
     * 计算TF_IDF
     *
     * @param TF  词频
     * @param IDF 逆文档率
     * @return
     */
    public Map<String, Map<String, Double>> getTFIDF(Map<String, Map<String, Double>> TF, Map<String, Map<String, Double>> IDF) {
        Map<String, Map<String, Double>> result = Maps.newHashMap();

        Set<String> keys = TF.keySet();

        for (String s : keys) {
            Map<String, Double> TFIDF = Maps.newHashMap();

            Map<String, Double> tf = TF.get(s);
            Map<String, Double> idf = IDF.get(s);

            Set<String> keyList = tf.keySet();
            for (String t : keyList) {
                TFIDF.put(t, tf.get(t) * idf.get(t));
                System.out.println(s + "\t\t --  " + t + "\t\t\t" + TFIDF.get(t));
            }
            result.put(s, TFIDF);
        }

        return result;
    }

    /**
     * 保存TFIDF
     *
     * @param freTF
     * @param freIDF
     * @param tfidf
     */
    private void saveTFIDF(
            Map<String, Map<String, Integer>> fre,
            Map<String, Map<String, Double>> freTF,
            Map<String, Map<String, Double>> freIDF,
            Map<String, Map<String, Double>> tfidf) {

        Set<String> titles = tfidf.keySet();

        for (String s : titles) {
            Map<String, Integer> fr = fre.get(s);
            Map<String, Double> ti = tfidf.get(s);
            Map<String, Double> ft = freTF.get(s);
            Map<String, Double> fd = freIDF.get(s);

            Set<String> tWord = fr.keySet();

            List<WordsTFIDF> words = new ArrayList<>();

            Integer i = 0;
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
                i++;
            }
            newsMapper.insertWords(words);
        }


    }

    @Test
    public void testMath() {
        Integer i = 2;
        Integer j = 329;
        Double m = Double.valueOf(i / j);
        System.out.println(m);
    }

}
