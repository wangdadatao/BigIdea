package com.datao.bigidea.test;

import com.datao.bigidea.BaseTest;
import com.datao.bigidea.utils.Similarity;
import com.datao.bigidea.utils.TFIDF;
import org.eclipse.jetty.util.ajax.JSON;
import org.junit.Test;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;

/**
 * Created by 王 海涛 on 2016/11/22.
 */
public class SimilarityTest extends BaseTest {


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

}
