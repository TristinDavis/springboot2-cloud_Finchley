package com.aha.tech.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: luweihong
 * @Date: 2018/8/8
 */
@Configuration
public class ThreadPoolConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolConfiguration.class);

    /**
     * 专门用于执行定时任务的线程池
     * 只要有@scheduler标签则会使用该线程池
     * @return
     */
    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        taskScheduler.setRemoveOnCancelPolicy(true);
        taskScheduler.setErrorHandler(t -> LOGGER.error("Unexpected error occurred in scheduled task.", t));
        taskScheduler.initialize();
        return taskScheduler;
    }

    @Bean("coreThreadPool")
    public ThreadPoolTaskExecutor coreThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(100);
        executor.setMaxPoolSize(200);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("core-threadPool-");

        return executor;
    }


    @Bean("nonCoreThreadPool")
    public ThreadPoolTaskExecutor nonCoreThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("non-core-threadPool-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        return executor;
    }
}
