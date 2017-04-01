package com.datao.bigidea.mapper;

import com.datao.bigidea.entity.BingImg;

/**
 * Created by 王 海涛 on 2017/4/1.
 * <p>
 * BingImgMapper
 */
public interface BingImgMapper {


    void insert(BingImg bingImg);

    /**
     * 获取首页图片
     *
     * @return 结果
     */
    BingImg getIndexImg();
}
