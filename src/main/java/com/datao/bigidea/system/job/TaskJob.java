package com.datao.bigidea.system.job;

import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by 王 海涛 on 2017/3/20.
 * <p>
 * 定时任务
 */
@Service
public class TaskJob {


    @Scheduled(cron = "8 * * * * ?")
    public void updateGreenSource() {
        System.out.println("test 定时任务！" + new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
    }


}
