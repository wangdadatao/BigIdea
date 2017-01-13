package com.datao.bigidea.test;

import com.datao.bigidea.BaseTest;
import com.datao.bigidea.entity.Movie;
import com.datao.bigidea.mapper.MovieMapper;
import com.datao.bigidea.utils.HttpUtil;
import com.google.common.collect.Maps;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.json.JSONObject;
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

    // 代理隧道验证信息
    final static String ProxyUser = "H76I1B601078978D";
    final static String ProxyPass = "68CD0F65620F0D3F";

    // 代理服务器
    final static String ProxyHost = "proxy.abuyun.com";
    final static Integer ProxyPort = 9020;

    // 设置IP切换头
    final static String ProxyHeadKey = "Proxy-Switch-Ip";
    final static String ProxyHeadVal = "yes";


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
            Document document = getDoucment(m.getDoubanUrl());
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
            Document document = getDoucment("http://movie.douban.com/tag/?view=cloud");
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
                document = getDoucment(tagUrl);
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
     * 传入URL 得到Document对象；
     *
     * @param url 传入URL
     * @return
     * @throws IOException
     */
    public Document getDoucment(String url) throws IOException {
        System.out.println(url);

        // Authenticator.setDefault(new Authenticator() {
        //     public PasswordAuthentication getPasswordAuthentication() {
        //         return new PasswordAuthentication(ProxyUser, ProxyPass.toCharArray());
        //     }
        // });
        // Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ProxyHost, ProxyPort));


        Connection con = Jsoup.connect(url);//获取连接
        con.header("User-Agent", getUserAgents());//配置模拟浏览器
        con.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        con.header("Accept-Encoding", "gzip, deflate, sdch, br");
        con.header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
        // con.header("Cookie", "ll=\"108169\"; bid=913hqGZRoVU; ps=y; push_noty_num=0; push_doumail_num=0; ap=1; viewed=\"2567698\"; gr_user_id=7c958f18-6d64-43c5-8083-9b70789b94bf; ct=y; __utmt=1; __utma=30149280.203547449.1484036285.1484211056.1484271809.15; __utmb=30149280.2.10.1484271809; __utmc=30149280; __utmz=30149280.1484271809.15.4.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; __utmv=30149280.5238; __utma=223695111.1757280608.1484036291.1484211738.1484272109.13; __utmb=223695111.0.10.1484272109; __utmc=223695111; __utmz=223695111.1484211738.12.5.utmcsr=douban.com|utmccn=(referral)|utmcmd=referral|utmcct=/misc/sorry; _pk_ref.100001.4cf6=%5B%22%22%2C%22%22%2C1484272110%2C%22https%3A%2F%2Fwww.douban.com%2Fmisc%2Fsorry%3Foriginal-url%3Dhttps%253A%252F%252Fmovie.douban.com%252F%22%5D; _pk_id.100001.4cf6=10f77013632f552b.1484036291.13.1484272110.1484211741.; _pk_ses.100001.4cf6=*; _vwo_uuid_v2=CAF8E9E209E4834AD9F777B003A2C406|3415df9a9ca6324839afc3dac518f487");
        //  con.proxy(proxy);
        con.timeout(5000);
        con.followRedirects(true);
        Connection.Response rs = con.execute();//获取响应
        return Jsoup.parse(rs.body());

    }

    /**
     * 模拟不同干的UA
     * @return 返回UA信息
     */
    public String getUserAgents() {
        String[] agents = {
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; AcooBrowser; .NET CLR 1.1.4322; .NET CLR 2.0.50727)",
                "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0; Acoo Browser; SLCC1; .NET CLR 2.0.50727; Media Center PC 5.0; .NET CLR 3.0.04506)",
                "Mozilla/4.0 (compatible; MSIE 7.0; AOL 9.5; AOLBuild 4337.35; Windows NT 5.1; .NET CLR 1.1.4322; .NET CLR 2.0.50727)",
                "Mozilla/5.0 (Windows; U; MSIE 9.0; Windows NT 9.0; en-US)",
                "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET CLR 2.0.50727; Media Center PC 6.0)",
                "Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET CLR 1.0.3705; .NET CLR 1.1.4322)",
                "Mozilla/4.0 (compatible; MSIE 7.0b; Windows NT 5.2; .NET CLR 1.1.4322; .NET CLR 2.0.50727; InfoPath.2; .NET CLR 3.0.04506.30)",
                "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN) AppleWebKit/523.15 (KHTML, like Gecko, Safari/419.3) Arora/0.3 (Change: 287 c9dfb30)",
                "Mozilla/5.0 (X11; U; Linux; en-US) AppleWebKit/527+ (KHTML, like Gecko, Safari/419.3) Arora/0.6",
                "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.2pre) Gecko/20070215 K-Ninja/2.1.1",
                "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9) Gecko/20080705 Firefox/3.0 Kapiko/3.0",
                "Mozilla/5.0 (X11; Linux i686; U;) Gecko/20070322 Kazehakase/0.4.5",
                "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.8) Gecko Fedora/1.9.0.8-1.fc10 Kazehakase/0.5.6",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.20 (KHTML, like Gecko) Chrome/19.0.1036.7 Safari/535.20",
                "Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; fr) Presto/2.9.168 Version/11.52",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.11 (KHTML, like Gecko) Chrome/20.0.1132.11 TaoBrowser/2.0 Safari/536.11",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.71 Safari/537.1 LBBROWSER",
                "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; LBBROWSER)",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 732; .NET4.0C; .NET4.0E; LBBROWSER)",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.84 Safari/535.11 LBBROWSER",
                "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E)",
                "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; QQBrowser/7.0.3698.400)",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 732; .NET4.0C; .NET4.0E)",
                "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; SV1; QQDownload 732; .NET4.0C; .NET4.0E; 360SE)",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 732; .NET4.0C; .NET4.0E)",
                "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E)",
                "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.89 Safari/537.1",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.89 Safari/537.1",
                "Mozilla/5.0 (iPad; U; CPU OS 4_2_1 like Mac OS X; zh-cn) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8C148 Safari/6533.18.5",
                "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:2.0b13pre) Gecko/20110307 Firefox/4.0b13pre",
                "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:16.0) Gecko/20100101 Firefox/16.0",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11",
                "Mozilla/5.0 (X11; U; Linux x86_64; zh-CN; rv:1.9.2.10) Gecko/20100922 Ubuntu/10.10 (maverick) Firefox/3.6.10"};

        return agents[(int) (1 + Math.random() * (agents.length - 1))];

    }

    /**
     * 发送get请求，获得电影json对象
     */
    public String getJson(String id) {
        String url = "http://api.douban.com/v2/movie/" + id;
        String result = null;

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
