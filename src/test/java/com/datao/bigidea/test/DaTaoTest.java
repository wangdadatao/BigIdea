package com.datao.bigidea.test;

import com.datao.bigidea.serviceImpl.BaseService;
import com.datao.bigidea.utils.Similarity;
import org.junit.Test;

/**
 * Created by 王 海涛 on 2016/11/19.
 */
public class DaTaoTest extends BaseService {

    @Test
    public void testSimilarity(){
        System.out.println(Similarity.getSimilarity("你看起开很好吃","你看起来不好吃"));
        System.out.println(Similarity.LDistance("你看起开很好吃","你看起来不好吃"));
    }

    @Test
    public void testStr(){
        String str = "  abc  ddd 123";
        str = str.replace(" ","");
        System.out.println(str);
    }


}
