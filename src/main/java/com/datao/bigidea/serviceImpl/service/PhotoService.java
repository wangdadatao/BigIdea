package com.datao.bigidea.serviceImpl.service;

import com.datao.bigidea.entity.BingImg;
import org.springframework.stereotype.Service;

/**
 * Created by 王 海涛 on 2017/4/1.
 */

public interface PhotoService {

    BingImg getIndexImg();

    void captureBingImg();
}
