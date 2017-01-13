package com.datao.bigidea.test;

import com.datao.bigidea.BaseTest;
import com.datao.bigidea.entity.Movie;
import com.datao.bigidea.mapper.MovieMapper;
import com.datao.bigidea.utils.HttpUtil;
import com.google.common.collect.Maps;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 王 海涛 on 2017/1/10.
 */
public class DouBanTest extends BaseTest {

    @Resource
    private MovieMapper movieMapper;


    /**
     * 获得电影简单信息
     */
    @Test
    public void movieTest() {
        Map<String, String> tags = getTags();

        Set<String> tagNames = tags.keySet();
        for (String s : tagNames) {
            getMovieByTag(tags.get(s));
        }
    }

    @Test
    public void getMovieInfoByAPI() {
        List<Movie> movies = movieMapper.getMovieJson();
        for (Movie m : movies) {
            m.setJson(getJson(m.getDoubanID()));
            if (m.getJson() != null && m.getJson().length() >= 50) {
                movieMapper.updateJson(m);
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获得电影详细信息
     */
    @Test
    public void getMovieInfo() {
        //    List<Movie> movies = movieMapper.getMovie();

        for (int i = 1; i <= 1; i++) {
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    getPageNum(finalI);
                }
            }).start();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            Thread.sleep(3600000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void getPageNum(Integer num) {
        System.out.println("创建了一个线程！" + num);

        List<Movie> movies = new ArrayList<>();
        movies = movieMapper.getMovie((num - 1) * 100, 100);
       /* if (num == 1) {
            movies = movieMapper.getMovie(0, 500);
        } else if (num == 2) {
            movies = movieMapper.getMovie(500, 500);
        } else if (num == 3) {
            movies = movieMapper.getMovie(1000, 500);
        } else if (num == 4) {
            movies = movieMapper.getMovie(1500, 500);
        } else if (num == 5) {
            movies = movieMapper.getMovie(2000, 500);
        }*/

        for (int i = 0; i < movies.size(); i++) {
            System.out.println(i + " --> " + movies.size() + "  线程： " + num);
            updateMovic(movies.get(i));
        }

    }

    //
    public void updateMovic(Movie m) {
        try {
            Document document = HttpUtil.getDocument(m.getDoubanUrl());
            Element titleE = document.select("title").get(0);
            m.setName(titleE.text().replace("(豆瓣)", "")); //设置电影名

            Element infoE = document.getElementById("info");

            try {
                //设置导演
                Elements dirE = infoE.getElementsContainingText("导演").last().parent().select("a");
                for (Element e : dirE) {
                    m.setDirector(m.getDirector() + "[" + e.attr("href").split("/")[2] + ":" + e.text() + "];");
                }
                m.setDirector(m.getDirector().replace("null", ""));
            } catch (NullPointerException ignored) {
            }

            try {
                //设置编剧
                Elements scriptwriterE = infoE.getElementsContainingText("编剧").last().parent().select("a");
                for (Element e : scriptwriterE) {
                    m.setScriptwriter(m.getScriptwriter() + "[" + e.attr("href").split("/")[2] + ":" + e.text() + "];");
                }
                m.setScriptwriter(m.getScriptwriter().replace("null", ""));
            } catch (NullPointerException ignore) {
            }

            try {
                //设置主演
                Elements protagonistE = infoE.getElementsContainingText("主演").last().parent().select("a");
                for (Element e : protagonistE) {
                    m.setProtagonist(m.getProtagonist() + "[" + e.attr("href").split("/")[2] + ":" + e.text() + "];");
                }
                m.setProtagonist(m.getProtagonist().replace("null", ""));
            } catch (NullPointerException ignore) {
            }


            try {
                //设置类型
                Elements typeE = infoE.select("span[property='v:genre']");
                for (Element e : typeE) {
                    m.setType(m.getType() + e.text() + "/");
                }
                m.setType(m.getType().replace("null", ""));
            } catch (NullPointerException ignore) {
            }

            try {
                //设置上映日期
                Elements upDates = infoE.select("span[property='v:initialReleaseDate']");
                for (Element e : upDates) {
                    m.setReleaseTime(m.getReleaseTime() + "/" + e.text() + "/");
                }
                m.setReleaseTime(m.getReleaseTime().replace("null", ""));
            } catch (NullPointerException ignore) {
            }

            try {
                //设置片长
                Element runTime = infoE.select("span[property='v:runtime']").get(0);
                m.setLongTime(runTime.text());
            } catch (RuntimeException ignore) {
            }


            Element sourInfo = document.getElementById("interest_sectl");

            try {
                //设置平均分
                Element sourceE = document.getElementsByClass("rating_num").get(0);
                m.setSource(sourceE.text());
            } catch (Exception ignore) {
            }


            try {
                //设置5星
                Elements stars = sourInfo.getElementsByClass("rating_per");
                m.setFiveStar(stars.get(0).text());
                m.setFourStar(stars.get(1).text());
                m.setThreeStar(stars.get(2).text());
                m.setTwoStar(stars.get(3).text());
                m.setOneStar(stars.get(4).text());
            } catch (RuntimeException ignore) {
            }


            try {
                //设置简介；
                Element introE = document.select("span[property='v:summary']").get(0);
                m.setIntro(introE.text());
            } catch (IndexOutOfBoundsException ignore) {
            }


            //设置海报
            Element imgE = document.getElementById("mainpic").select("img").get(0);
            m.setImg(imgE.attr("src"));


            movieMapper.updateMovie(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取所有的标签
    public Map<String, String> getTags() {
        Map<String, String> tags = Maps.newHashMap();
        try {
            Document document = HttpUtil.getDocument("http://movie.douban.com/tag/?view=cloud");
            Elements elements = document.getElementsByClass("tagCol").select("a");

            for (Element e : elements) {
                tags.put(e.text(), "https://movie.douban.com" + e.attr("href"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tags;
    }

    /**
     * 查询该标签下的所有电影 （电影ID、电影连接）
     *
     * @param tagUrl 标签URL
     * @return
     */
    public List<Movie> getMovieByTag(String tagUrl) {
        Boolean bool = true;

        while (bool) {
            List<Movie> movies = new ArrayList<>();
            Document document = null;
            try {
                document = HttpUtil.getDocument(tagUrl);
                Elements elements = document.getElementsByClass("pl2").select("a");

                for (Element e : elements) {
                    String url = e.attr("href");
                    String[] sp = url.split("/");
                    if (sp.length >= 5) {
                        Movie movie = new Movie();
                        movie.setDoubanID(url.split("/")[4]);
                        movie.setDoubanUrl(url.replace("https", "http"));
                        movies.add(movie);
                    }
                }
                movieMapper.insert(movies);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (document != null) {
                Element element = document.getElementsByClass("next").select("a").first();
                if (element != null) {
                    tagUrl = element.attr("href");
                } else {
                    bool = false;
                }
            }
        }
        return null;
    }




    /**
     * 发送get请求，获得电影json对象
     */
    public String getJson(String id) {
        String url = "http://api.douban.com/v2/movie/" + id;
        String result = null;

        // 代理隧道验证信息
        final  String ProxyUser = "H76I1B601078978D";
        final  String ProxyPass = "68CD0F65620F0D3F";

        // 代理服务器
        final  String ProxyHost = "proxy.abuyun.com";
        final  Integer ProxyPort = 9020;

        Authenticator.setDefault(new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(ProxyUser, ProxyPass.toCharArray());
            }
        });
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ProxyHost, ProxyPort));


        Connection con = Jsoup.connect(url);//获取连接

        try {
            Document document = Jsoup.connect(url).ignoreContentType(true).proxy(proxy).timeout(3000).get();

            System.out.println(document.text());
            result = document.text();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return decode(result);
    }


    /**
     * 处理返回的json编码问题
     *
     * @param unicodeStr
     * @return
     */
    public String decode(String unicodeStr) {
        if (unicodeStr == null) {
            return null;
        }
        StringBuffer retBuf = new StringBuffer();
        int maxLoop = unicodeStr.length();
        for (int i = 0; i < maxLoop; i++) {
            if (unicodeStr.charAt(i) == '\\') {
                if ((i < maxLoop - 5)
                        && ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr
                        .charAt(i + 1) == 'U')))
                    try {
                        retBuf.append((char) Integer.parseInt(
                                unicodeStr.substring(i + 2, i + 6), 16));
                        i += 5;
                    } catch (NumberFormatException localNumberFormatException) {
                        retBuf.append(unicodeStr.charAt(i));
                    }
                else
                    retBuf.append(unicodeStr.charAt(i));
            } else {
                retBuf.append(unicodeStr.charAt(i));
            }
        }
        return retBuf.toString();
    }

}
