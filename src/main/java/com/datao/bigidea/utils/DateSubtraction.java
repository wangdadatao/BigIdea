package com.datao.bigidea.utils;


import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateSubtraction {


    /**
     * 获取两个时间的天数差
     *
     * @param oldDate 第一个时间
     * @param newDate 第二个时间
     * @return 返回的小时结果
     * @throws ParseException
     */
    public static Integer getSubDays(String oldDate, String newDate) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateTime o = new DateTime(df.parse(oldDate));
        DateTime n = new DateTime(df.parse(newDate));
        Double hour =Math.ceil ((double) (n.getMillis() - o.getMillis()) / (3600 * 1000));
        return (int) Math.ceil(hour / 24);
    }

    /**
     * 获取两个时间的毫秒差
     *
     * @param oldDate 第一个时间
     * @param newDate 第二个时间
     * @return 毫秒差
     * @throws ParseException
     */
    public static Long getMilli(String oldDate, String newDate) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateTime o = new DateTime(df.parse(oldDate));
        DateTime n = new DateTime(df.parse(newDate));
        return (n.getMillis() - o.getMillis());
    }


}
