package com.datao.bigidea.mapper;

import com.datao.bigidea.entity.Wallpaper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 王 海涛 on 2017/4/7.
 * <p>
 * 壁纸mapper
 */
public interface WallpaperMapper {

    /**
     * 插入新的壁纸信息
     *
     * @param wallpaperList
     */
    void insertList(List<Wallpaper> wallpaperList);

    /**
     * 查询所有的图片
     *
     * @return
     */
    List<Wallpaper> selectAll();

    /**
     * 更新壁纸信息
     *
     * @param wallpaper
     */
    void update(Wallpaper wallpaper);
}
