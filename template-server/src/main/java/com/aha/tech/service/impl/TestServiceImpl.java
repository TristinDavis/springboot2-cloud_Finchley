package com.aha.tech.service.impl;

import com.aha.tech.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Author: luweihong
 * @Date: 2018/7/25
 */
@Service
public class TestServiceImpl implements TestService {

    private Logger LOGGER = LoggerFactory.getLogger(TestServiceImpl.class);

    @Async
    @Override
    public void printTest() {
        LOGGER.debug("这个service 会使用 异步线程池,需要@Async,观察线程池的名称 区别是否生效");
        LOGGER.info("这是info日志");
        LOGGER.debug("这是debug日志");
        LOGGER.warn("这是warn日志");
        LOGGER.error("这是error日志");
    }
}
