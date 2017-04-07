package com.datao.bigidea.test;

import com.datao.bigidea.BaseTest;
import org.joda.time.DateTime;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by 王 海涛 on 2017/4/7.
 */
public class PhotoTest extends BaseTest {

    /**
     * 压缩成8*8
     */
    @Test
    public void zipPhoto() {

        try {
            BufferedImage src = ImageIO.read(new File("f:/imgs", "8d965176-a8b3-4932-a8a3-9747438a432d.jpg")); // 读入文件
            int width = src.getWidth(); // 得到源图宽
            int height = src.getHeight(); // 得到源图长


            System.out.println(width + "  ---->  " + height);

            width = 8;
            height = 8;

            Image image = src.getScaledInstance(width, height,
                    Image.SCALE_DEFAULT);
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            ImageIO.write(tag, "JPEG", new File("f:/abc.jpg"));// 输出到文件流


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
