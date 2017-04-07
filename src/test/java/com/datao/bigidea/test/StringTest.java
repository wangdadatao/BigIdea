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

    @Test
    public void replaceTest() {
        String str = "1011010111001101111000111101010101110010110010101011101111000101";
        int[][] attr = new int[8][8];

        char[] chars = str.toCharArray();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                attr[i][j] = Integer.valueOf(String.valueOf(chars[i * 8 + j]));
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i += 2) {
            for (int j = 0; j < 8; j += 2) {
                String oooo = "" + attr[i][j] + attr[i][j + 1] + attr[i + 1][j] + attr[i + 1][j + 1];
                System.out.println(oooo);
            }
        }

        System.out.println(attr[0][0]);
    }

}
