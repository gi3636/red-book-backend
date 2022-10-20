package com.example.red.book.config;

import com.example.red.book.constant.NoteConstant;
import com.example.red.book.quartz.NoteFavoriteTask;
import com.example.red.book.quartz.NoteLikeTask;
import com.example.red.book.service.impl.SchedulerServiceImpl;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    private static final String LIKE_TASK_IDENTITY = NoteConstant.LIKE_TASK_QUARTZ;

    @Autowired
    SchedulerServiceImpl schedulerService;

    @Bean
    public void quartzDetail() {
        TimerInfo timerInfo = new TimerInfo();
        timerInfo.setRepeatIntervalMs(10000L);
        timerInfo.setRunForever(true);
        schedulerService.schedule(NoteLikeTask.class, timerInfo);
    }

    @Bean
    public void favoriteTask() {
        TimerInfo timerInfo = new TimerInfo();
        timerInfo.setRepeatIntervalMs(5000L);
        timerInfo.setRunForever(true);
        schedulerService.schedule(NoteFavoriteTask.class, timerInfo);
        //return JobBuilder.newJob(NoteFavoriteTask.class).withIdentity(NoteConstant.FAVORITE_TASK_QUARTZ).storeDurably().build();
    }

    //@Bean
    //public Trigger quartzTrigger() {
    //    SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
    //            .withIntervalInSeconds(10)  //设置时间周期单位秒
    //            //.withIntervalInHours(2)  //两个小时执行一次
    //            .repeatForever();
    //    return TriggerBuilder.newTrigger()
    //            .forJob(quartzDetail())
    //            .forJob(favoriteTask())
    //            .withIdentity(LIKE_TASK_IDENTITY)
    //            .withSchedule(scheduleBuilder)
    //            .build();
    //}
}