package com.boot;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author qiguangjie
 * 代码中指定执行那个JOB
 * spring.batch.job.enabled=false
 *
 */
@SpringBootApplication
@EnableBatchProcessing
public class SpringBatchApplication {

    public static void main(String[] args) throws BeansException, JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        // 下面代码是全部执行
        //        SpringApplication.run(SpringBatchApplication.class, args);
        /*
         * 下面的代码是指定Job号执行
         * spring.batch.job.enabled=false 配置文件加入
         */
        SpringApplication application = new SpringApplication(SpringBatchApplication.class);
        //        application.setWebEnvironment(false);
        ConfigurableApplicationContext ctx = application.run(args);
        JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
        JobParameters jobParameters = new JobParametersBuilder()
                .addDate("date", new Date())
                .toJobParameters();
        System.out.println("qgj excute JOB Start--------------------------- ");
        jobLauncher.run(ctx.getBean("importUserJob", Job.class), jobParameters);
        System.out.println("qgj excute JOB end  --------------------------- ");

    }
}
