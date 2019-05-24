package com.github.fish56.issues.job;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzJobConfig {
    /**
     * 每个60秒触发一次
     */
    private static int issuesSyncInterval = 30 * 60;
    @Bean
    public JobDetail issuesSyncJobDetail(){
        return JobBuilder.newJob(IssuesSyncJob.class)
                //.withIdentity("vue", "vuejsJob")
                .storeDurably(true)
                .build();
    }
    @Bean
    public Trigger triggerIssuesJob(JobDetail jobDetail){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
                .simpleSchedule().withIntervalInSeconds(issuesSyncInterval).repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                //.withIdentity("vue","vuejsTrigger")
                .withSchedule(scheduleBuilder).startNow().build();
    }
}
