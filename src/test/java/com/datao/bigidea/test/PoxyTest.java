package com.datao.bigidea.test;

import java.io.IOException;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PoxyTest {
    // 代理隧道验证信息
    final static String ProxyUser = "H76I1B601078978D";
    final static String ProxyPass = "68CD0F65620F0D3F";

    // 代理服务器
    final static String ProxyHost = "proxy.abuyun.com";
    final static Integer ProxyPort = 9010;

    // 设置IP切换头
    final static String ProxyHeadKey = "Proxy-Switch-Ip";
    final static String ProxyHeadVal = "yes";

    public static String getUrlProxyContent(String url) {
        Authenticator.setDefault(new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(ProxyUser, ProxyPass.toCharArray());
            }
        });

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ProxyHost, ProxyPort));

        try {
            // 此处自己处理异常、其他参数等
//            Connection con = Jsoup.connect(url);//获取连接
//            con.header("User-Agent", "Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; fr) Presto/2.9.168 Version/11.52");//配置模拟浏览器
//            con.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//            con.header("Accept-Encoding", "gzip, deflate, sdch, br");
//            con.header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
//            con.header("Cookie","ll=\"108169\"; bid=913hqGZRoVU; ap=1; ps=y; _vwo_uuid_v2=CAF8E9E209E4834AD9F777B003A2C406|3415df9a9ca6324839afc3dac518f487; _pk_ref.100001.4cf6=%5B%22%22%2C%22%22%2C1484112737%2C%22https%3A%2F%2Fwww.douban.com%2Fmisc%2Fsorry%3Foriginal-url%3Dhttps%253A%252F%252Fmovie.douban.com%252Ftag%252F%22%5D; _pk_id.100001.4cf6=10f77013632f552b.1484036291.4.1484112737.1484108194.; _pk_ses.100001.4cf6=*; __utma=30149280.203547449.1484036285.1484108187.1484112737.4; __utmb=30149280.0.10.1484112737; __utmc=30149280; __utmz=30149280.1484036285.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utma=223695111.1757280608.1484036291.1484108194.1484112737.4; __utmb=223695111.0.10.1484112737; __utmc=223695111; __utmz=223695111.1484108194.3.3.utmcsr=douban.com|utmccn=(referral)|utmcmd=referral|utmcct=/misc/sorry");
//
//            con.proxy(proxy);
//            Connection.Response rs = con.execute();//获取响应
//            Document doc = Jsoup.parse(rs.body());

            // 此处自己处理异常、其他参数等
            Document doc = Jsoup.connect(url).timeout(3000).proxy(proxy).get();


            if (doc != null) {
                System.out.println(doc.body().html());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) throws Exception {
        // 要访问的目标页面
         //String targetUrl = "http://test.abuyun.com/proxy.php";
        String targetUrl = "http://movie.douban.com/tag/?view=cloud";
        //String targetUrl = "http://proxy.abuyun.com/current-ip";

        getUrlProxyContent(targetUrl);
    }
}