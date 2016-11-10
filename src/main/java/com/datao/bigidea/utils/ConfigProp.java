package com.datao.bigidea.utils;


import java.io.IOException;
import java.util.Properties;

public class ConfigProp {

    private static Properties properties = new Properties();

    static {
        try {
            properties.load(ConfigProp.class.getClassLoader().getResourceAsStream("base.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

}