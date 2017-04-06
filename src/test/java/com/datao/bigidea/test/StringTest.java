package com.datao.bigidea.test;

import com.datao.bigidea.BaseTest;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王 海涛 on 2017/3/21.
 * <p>
 * 字符串测试类
 */

public class StringTest extends BaseTest {


    @Test
    public void arrayTest() {
        String s1 = "Programming";
        String s2 = new String("Programming");
        String s3 = "Program" + "ming";
        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s1 == s1.intern());
    }

    @Test
    public void subStringTest() {
        String str = "//img.hb.aicdn.com/47d9808e40ca9b1c37090ee95922113a04bab88a2e51c-4eQlTb_sq320";
        str = str.substring(0, 2);
        System.out.println(str.equals("//"));
    }

}
