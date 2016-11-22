package com.datao.bigidea.test;

import com.datao.bigidea.BaseTest;
import com.datao.bigidea.mapper.NewsMapper;
import com.datao.bigidea.serviceImpl.service.SpiderService;
import com.datao.bigidea.utils.contentextractor.ContentExtractor;
import com.datao.bigidea.entity.News;
import com.google.common.collect.Maps;
import org.eclipse.jetty.util.ajax.JSON;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.SocketException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 王 海涛 on 2016/11/22.
 */
public class SpiderTest extends BaseTest {


    @Resource
    private SpiderService spiderService;

    @Test
    public void testGetUrl() {
        Map<String, String> navUrl = Maps.newHashMap();
        try {
            Document document = Jsoup.connect("http://www.ruanyifeng.com/blog/essays/").get();
            Elements elements = document.select("ul.module-list").select("li a");

            for (Element e : elements) {
                String url = e.attr("href");
                if (!url.contains("20")) {
                    navUrl.put(e.text(), url);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(JSON.toString(navUrl));

        Set<String> navName = navUrl.keySet();
        Map<String, String> allUrl = Maps.newHashMap();

        for (String s : navName) {
            try {
                Document document = Jsoup.connect(navUrl.get(s)).get();
                Elements elements = document.select("ul.module-list").select("li a");

                for (Element e : elements) {
                    String url = e.attr("href");
                    if (url.contains("20")) {
                        System.out.println(url);
                        News news = new News();
                        news.setTitle(e.text());
                        news.setUrl(url);
                        news.setCreateTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
                        news.setType(s);
                        spiderService.insert(news);

                       /* News news = ContentExtractor.getNewsByUrl(url);
                        news.setTitle(e.text());
                        news.setCreateTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
                        news.setType(s);
                        news.setContentEle(news.getContentElement().toString());

                        spiderService.insert(news);
                        Thread.sleep(2000);*/
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void testUrl() {
        try {
            Document document = Jsoup.connect("http://www.ruanyifeng.com/blog/essays/").get();
            Elements elements = document.select("title");

            System.out.println(elements.get(0).text());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateTestBlog() {
        List<News> newses = spiderService.queryAllNews();
        for (News n : newses) {
            try {
               /* Document document = Jsoup.connect(n.getUrl()).get();
                System.out.println(document.body());*/
                News news = ContentExtractor.getNewsByUrl(n.getUrl());
                n.setContentEle(news.getContentElement().toString());
                n.setContent(news.getContent());
                n.setTime(news.getTime());
                n.setCreateTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
                spiderService.update(n);
                Thread.sleep(200);
            } catch (Exception e) {
                e.printStackTrace();
                if (e instanceof SocketException) {
                    System.out.println(n.getUrl());
                    return;
                }

                System.out.println(n.getUrl());
            }
        }


    }
}
