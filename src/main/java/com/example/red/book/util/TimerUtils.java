package com.example.red.book.util;

import com.example.red.book.config.TimerInfo;
import org.quartz.*;

import javax.xml.crypto.Data;
import java.util.Date;

public class TimerUtils {

    public static JobDetail buildJobDetail(final Class jobClass, final TimerInfo info) {

        final JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(jobClass.getSimpleName(), info);

        return JobBuilder.newJob(jobClass)
                .withIdentity(jobClass.getSimpleName())
                .setJobData(jobDataMap)
                .build();
    }


    public static Trigger buildTrigger(final Class jobClass, final TimerInfo info) {
        SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(info.getRepeatIntervalMs());
        if (info.getRunForever()) {
            builder = builder.repeatForever();
        } else {
            builder = builder.withRepeatCount(info.getTotalFireCount() - 1);
        }
        return TriggerBuilder
                .newTrigger()
                .withIdentity(jobClass.getSimpleName())
                .withSchedule(builder)
                .startAt(new Date(System.currentTimeMillis() * info.getInitialOffsetMs()))
                .build();
    }
}
