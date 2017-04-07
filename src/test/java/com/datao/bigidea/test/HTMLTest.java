package com.datao.bigidea.test;

import com.datao.bigidea.BaseTest;
import com.datao.bigidea.entity.News;
import com.datao.bigidea.utils.HttpUtil;
import com.datao.bigidea.utils.contentextractor.ContentExtractor;
import com.google.common.collect.Sets;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.util.StringUtils;

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
        String baseUrl = "http://www.3gbizhi.com/lists-%E5%85%A8%E9%83%A8/2.html";
        try {
            Document document = HttpUtil.getDocument(baseUrl);
            Elements elements = document.select("li img");
            System.out.println(elements.size());

            Set<String> urls = Sets.newHashSet();
            for (Element e : elements) {
                String src = e.attr("src");

                if (StringUtils.isEmpty(src)) {
                    src = e.attr("_src");
                    if (StringUtils.isEmpty(src)) {
                        continue;
                    }
                }

                if (src.substring(0, 2).equals("//")) {
                    src = "http:" + src;
                }

                if (src.substring(0, 1).equals("/") && !src.substring(0, 2).equals("//")) {
                    src = baseUrl + src;
                }
                src = src.replace(".255.344.jpg", "");
                System.out.println(src);

                urls.add(src);
            }

            for (String src : urls) {
                String imageName = UUID.randomUUID().toString() + ".jpg";

                InputStream is = null;
                try {
                    is = HttpUtil.getInputStream(src);
                } catch (FileNotFoundException e) {
                    continue;
                }
                OutputStream os = new FileOutputStream(new File("j://imgs", imageName));

                byte[] buf = new byte[1024];

                int l = 0;
                while ((l = is.read(buf)) != -1) {
                    os.write(buf, 0, l);
                }
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
