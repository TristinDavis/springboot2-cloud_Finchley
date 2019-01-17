package com.aha.tech.controller;

import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: luweihong
 * @Date: 2018/10/23
 */
@Api(tags = "应用指令处理器")
@RestController
public class CommandController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandController.class);

    @Autowired(required = false)
    private KafkaListenerEndpointRegistry registry;

    @GetMapping(value = "/command/kafka/on")
    public String kafkaConsumerOn(@RequestParam(value = "listener_id") String listenerId) {
        LOGGER.info("Received kafka listener enable on, params : {}", listenerId);
        Preconditions.checkArgument(StringUtils.isNotBlank(listenerId), "缺失listener_id");
        String operation = "ok";
        try {
            if (registry.getListenerContainer(listenerId).isContainerPaused()) {
                registry.getListenerContainer(listenerId).resume();
            }
            if (!registry.getListenerContainer(listenerId).isRunning()) {
                registry.getListenerContainer(listenerId).start();
                LOGGER.info("开启kafka consumer 操作成功");
            }
        } catch (Exception e) {
            LOGGER.error("开启kafka操作失败", e);
            operation = "not ok";
        }

        return operation;
    }

    @GetMapping(value = "/command/kafka/off")
    public String kafkaConsumerOff(@RequestParam(value = "listener_id") String listenerId) {
        LOGGER.info("Received kafka listener enable off, params : {}", listenerId);
        Preconditions.checkArgument(StringUtils.isNotBlank(listenerId), "缺失listener_id");
        String operation = "ok";
        try {
            if (!registry.getListenerContainer(listenerId).isContainerPaused()) {
                registry.getListenerContainer(listenerId).pause();
            }
            if (registry.getListenerContainer(listenerId).isRunning()) {
                registry.getListenerContainer(listenerId).stop();
                LOGGER.info("关闭kafka consumer 操作成功");
            }
        } catch (Exception e) {
            LOGGER.error("关闭kafka操作失败", e);
            operation = "not ok";
        }

        return operation;
    }
}
