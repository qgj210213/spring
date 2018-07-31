package com.boot;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author qiguangjie
 * 1.利用配置文件执行脚本
 * 2.#启动时要执行的Job，默认执行全部Job
 * spring.batch.job.names = importUserJob
 *  #是否自动执行定义的Job，默认是
 * spring.batch.job.enabled=true
 *
 */
@SpringBootApplication
@EnableBatchProcessing
public class BatchApplication {

    public static void main(String[] args) throws BeansException, JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        // 下面代码是全部执行
        SpringApplication.run(SpringBatchApplication.class, args);

    }
}
