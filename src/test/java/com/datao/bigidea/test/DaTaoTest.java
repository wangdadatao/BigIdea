package com.datao.bigidea.test;

import com.datao.bigidea.serviceImpl.BaseService;
import com.datao.bigidea.utils.Similarity;
import com.datao.bigidea.utils.TFIDF;
import org.eclipse.jetty.util.ajax.JSON;
import org.junit.Test;

import java.util.Map;

/**
 * Created by 王 海涛 on 2016/11/19.
 */
public class DaTaoTest extends BaseService {

    @Test
    public void testSimilarity() {
        System.out.println(Similarity.getConSimilarity("你看起开很好吃吗", "你看起来不好吃"));
        System.out.println(Similarity.LDistance("你看起开很好吃吗", "你看起来不好吃"));
    }

    @Test
    public void testStr() {
        String str = "  abc  ddd 123";
        str = str.replace(" ", "");
        System.out.println(str);
    }


    @Test
    public void testKeyWord() {
        String str = "11月17日，林丹出轨事件引发热议。下午，林丹在微博中回应称：“做为一个男人我不为自己做更多的辩解，但是我的行为伤害了我的家人。在这里，我向我的家人道歉，对不起。”\n" +
                "　　据公开报道显示，近年来，林丹和谢杏芳以体坛爱情神话、模范夫妻、十年爱侣的身份拍过无数广告，上过节目，跑过通告，还拍过杂志，几乎完成了由运动员向娱乐圈人士的转型。\n" +
                "\n" +
                "　　同时，林丹对商业道路的探索转型也极为顺利。据天眼查数据显示，林丹成立了上海创领体育策划工作室和炫励（上海）商贸有限公司，间接投资了丝路云商网络科技有限公司和南京界内体育科技有限公司。据知情人透露，林丹早已和某知名公司达成意向，现金出资成立了专项基金投资影视剧及衍生品。";


        Map<String, Integer> result = TFIDF.segStr(str);

        System.out.println(JSON.toString(result));

    }


}
