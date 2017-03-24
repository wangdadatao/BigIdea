package com.datao.bigidea.test;

import com.datao.bigidea.BaseTest;
import com.datao.bigidea.entity.News;
import com.datao.bigidea.utils.contentextractor.ContentExtractor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

/**
 * Created by 王 海涛 on 2016/12/27.
 */
public class HTMLTest extends BaseTest {

    @Test
    public void testGetHTMLContent() {
        String html = "";
        Document coc = Jsoup.parse("<body id='insertBody'>" + html + "</body>");
        Element element = coc.getElementById("insertBody");
        System.out.println(element.text());
    }

    @Test
    public void testNewsContent() {
        String url = "http://blog.csdn.net/yongh701/article/details/46894417";
        try {
            News news = ContentExtractor.getNewsByUrl(url);
            System.out.println(news.getCreateTime());
            System.out.println(news.getContentElement().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
