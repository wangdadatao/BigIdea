package com.datao.bigidea.SpiderTest;

import com.datao.bigidea.BaseTest;
import com.datao.bigidea.entity.Wallpaper;
import com.datao.bigidea.mapper.WallpaperMapper;
import com.datao.bigidea.utils.HttpUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by 王 海涛 on 2017/4/7.
 * <p>
 * 爬取该网站全部下的150页图片,并计算每张图片的特征值
 * http://www.3gbizhi.com/lists-%E5%85%A8%E9%83%A8/
 */
public class SpiderPhoto extends BaseTest {

    @Resource
    private WallpaperMapper wallpaperMapper;

    @Test
    public void getImg() {
        for (int i = 1; i <= 150; i++) {
            String baseUrl = "http://www.3gbizhi.com/lists-%E5%85%A8%E9%83%A8/" + i + ".html";

            try {
                Document document = HttpUtil.getDocument(baseUrl);
                Elements elements = document.select("li img");

                Set<String> imgPaths = getImgPath(elements);

            } catch (IOException e) {
                System.out.println(baseUrl);
            }

        }
    }

    /**
     * 根据Elements获取图片地址
     *
     * @param elements
     * @return
     */
    public Set<String> getImgPath(Elements elements) {
        Set<String> urls = Sets.newHashSet();
        List<Wallpaper> wallpaperList = Lists.newArrayList();

        for (Element e : elements) {
            Wallpaper wallpaper = new Wallpaper();
            wallpaper.setBaseUrl(e.attr("src").replace(".255.344.jpg", "").replace("下载", ""));
            wallpaper.setName(e.attr("alt"));
            wallpaper.setCreateTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
            wallpaperList.add(wallpaper);
        }

        wallpaperMapper.insertList(wallpaperList);
        return urls;
    }

    @Test
    public void ab() {

    }

    @Test
    public void saveImg() {
        List<Wallpaper> wallpapers = wallpaperMapper.selectAll();

        for (Wallpaper w : wallpapers) {

            try {
                String src = w.getBaseUrl();
                String imageName = UUID.randomUUID().toString();
                if (src.contains(".jpg")) {
                    imageName += ".jpg";
                } else if (src.contains(".jpeg")) {
                    imageName += ".jepg";
                } else if (src.contains(".png")) {
                    imageName += ".png";
                }
                w.setUuidName(imageName);

                InputStream is = HttpUtil.getInputStream(src);

                OutputStream os = new FileOutputStream(new File("f://imgs", imageName));

                byte[] buf = new byte[1024];

                int l = 0;
                while ((l = is.read(buf)) != -1) {
                    os.write(buf, 0, l);
                }
                is.close();

                wallpaperMapper.update(w);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(w.getId());
            }
        }

    }


    //图片缩小成8*8
    @Test
    public void getImgWH() {
        List<Wallpaper> wallpapers = wallpaperMapper.selectAll();

        for (Wallpaper w : wallpapers) {

            try {
                BufferedImage src = ImageIO.read(new File("F:/imgs", w.getUuidName())); // 读入文件
                int width = src.getWidth();
                int height = src.getHeight();

                w.setWidth(width);
                w.setHeight(height);
                wallpaperMapper.update(w);

                width = 8;
                height = 8;

                Image image = src.getScaledInstance(width, height,
                        Image.SCALE_DEFAULT);
                BufferedImage tag = new BufferedImage(width, height,
                        BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(image, 0, 0, null); // 绘制缩小后的图
                g.dispose();
                ImageIO.write(tag, "JPEG", new File("f:/img", w.getUuidName()));// 输出到文件流

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(w.getId());
            }
        }


    }

    //图片转成64阶灰度
    @Test
    public void getBlack() {
        List<Wallpaper> wallpapers = wallpaperMapper.selectAll();

        for (Wallpaper w : wallpapers) {

            try {
                BufferedImage src = ImageIO.read(new File("F:/img", w.getUuidName())); // 读入文件
                int width = src.getWidth(); // 得到源图宽
                int height = src.getHeight(); // 得到源图长

                int alpha = 0xFF << 24;
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {

                        int original = src.getRGB(i, j);
                        int red = ((original & 0x00FF0000) >> 16);
                        int green = ((original & 0x0000FF00) >> 8);
                        int blue = (original & 0x000000FF);

                        int grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                        grey = alpha | (grey << 16) | (grey << 8) | grey;

                        src.setRGB(i, j, grey);
                    }
                }
                ImageIO.write(src, "JPEG", new File("f:/black", w.getUuidName()));


            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(w.getId());
            }
        }

    }

    //计算图片指纹
    @Test
    public void getFeature() {
        List<Wallpaper> wallpapers = wallpaperMapper.selectAll();

        for (Wallpaper w : wallpapers) {

            try {
                BufferedImage src = ImageIO.read(new File("F:/black", w.getUuidName())); // 读入文件
                int width = src.getWidth(); // 得到源图宽
                int height = src.getHeight(); // 得到源图长

                int avgPixel = 0;
                System.out.println(new DateTime().toString("HH:mm:ss.SSS"));
                 for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        avgPixel += src.getRGB(i, j);
                    }
                }
                avgPixel = avgPixel / 64;

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < width; i += 2) {
                    for (int j = 0; j < height; j += 2) {
                        if (avgPixel >= src.getRGB(i, j)) {
                            sb.append(1);
                        } else {
                            sb.append(0);
                        }

                        if (avgPixel >= src.getRGB(i, j + 1)) {
                            sb.append(1);
                        } else {
                            sb.append(0);
                        }

                        if (avgPixel >= src.getRGB(i + 1, j)) {
                            sb.append(1);
                        } else {
                            sb.append(0);
                        }

                        if (avgPixel >= src.getRGB(i + 1, j + 1)) {
                            sb.append(1);
                        } else {
                            sb.append(0);
                        }
                    }
                }

                w.setFeature(getFeatureHash(sb.toString()));
                wallpaperMapper.update(w);

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(w.getId());
            }
        }

    }

    public String getFeatureHash(String str) {
        if (str == null || str.equals("") || str.length() % 8 != 0)
            return null;
        StringBuilder sb = new StringBuilder();
        int iTmp;
        for (int i = 0; i < str.length(); i += 4) {
            iTmp = 0;
            for (int j = 0; j < 4; j++) {
                iTmp += Integer.parseInt(str.substring(i + j, i + j + 1)) << (4 - j - 1);
            }
            sb.append(Integer.toHexString(iTmp));
        }
        return sb.toString();
    }

    //计算图片指纹
    @Test
    public void getSimilarImg() {
        List<Wallpaper> wallpapers = wallpaperMapper.selectAll();

        for (Wallpaper a : wallpapers) {
            for (Wallpaper w : wallpapers) {
                if (StringUtils.isNotEmpty(a.getFeature()) && StringUtils.isNotEmpty(w.getFeature()) && !a.equals(w)) {
                    Integer simNum = diff(a.getFeature(), w.getFeature());
                    if (simNum <= 5) {
                        System.out.println(a.getUuidName() + " -->  " + w.getUuidName() + "  -->  " + simNum);
                    }
                }
            }
        }


    }

    public Integer diff(String s1, String s2) {
        char[] s1s = s1.toCharArray();
        char[] s2s = s2.toCharArray();
        int diffNum = 0;
        for (int i = 0; i < s1s.length; i++) {
            if (s1s[i] != s2s[i]) {
                diffNum++;
            }
        }
        return diffNum;
    }


    @Test
    public void useDiff() {
        System.out.println(diff("91b65e3864300817", "043478595425016d"));
    }
}
