package com.datao.bigidea.system.job;

import com.datao.bigidea.serviceImpl.service.PhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.inject.Inject;

/**
 * Created by 王 海涛 on 2017/3/20.
 * <p>
 * 定时任务
 */
@Service
public class TaskJob {

    Logger logger = LoggerFactory.getLogger(TaskJob.class);

    @Resource
    private PhotoService photoService;

    @Scheduled(cron = "0 3 * * * ?")
    public void updateGreenSource() {
        System.out.println("定时任务");
        try {
            photoService.captureBingImg();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
