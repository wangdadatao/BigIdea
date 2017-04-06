package com.datao.bigidea.test;

import com.datao.bigidea.BaseTest;
import com.datao.bigidea.entity.News;
import com.datao.bigidea.utils.contentextractor.ContentExtractor;
import com.google.common.collect.Sets;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Set;
import java.util.UUID;

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

    @Test
    public void getBingImg() {
        String baseUrl = "http://www.bing.com";
        String spUrl = "//hpimagearchive.aspx?format=xml&idx=0&n=1";
        try {
            Document document = Jsoup.connect(baseUrl + spUrl).get();
            Elements element = document.select("images>image>url");

            System.out.println(element.get(0).text());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getZealer() {
        String baseUrl = "http://huaban.com/favorite/photography/";
        try {
            Document document = Jsoup.connect(baseUrl).get();
            Elements elements = document.select("img");
            System.out.println(elements.size());

            Set<String> urls = Sets.newHashSet();
            for (Element e : elements) {
                String src = e.attr("src");

                if (src.substring(0, 2).equals("//")) {
                    src = "http:" + src;
                }

                if (src.substring(0, 1).equals("/") && !src.substring(0, 2).equals("//")) {
                    src = baseUrl + src;
                }
                urls.add(src);
            }

            for (String src : urls) {
                String imageName = UUID.randomUUID().toString() + ".jpg";

                URL url = new URL(src);
                URLConnection uri = url.openConnection();

                InputStream is = uri.getInputStream();
                OutputStream os = new FileOutputStream(new File("f://imgs", imageName));

                byte[] buf = new byte[1024];

                int l = 0;
                while ((l = is.read(buf)) != -1) {
                    os.write(buf, 0, l);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
