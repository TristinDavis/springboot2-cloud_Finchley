package com.aha.tech.message.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * @Author: luweihong
 * @Date: 2018/8/31
 */
@Component
public class KafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);


    @Autowired(required = false)
    private KafkaTemplate kafkaTemplate;

    /**
     * 使用KafkaTemplate向Kafka推送数据
     * @param topicName topic
     * @param data
     */
    public void sendMessage(String topicName, String data) {
        LOGGER.info(MessageFormat.format("开始向Kafka推送数据：{0}", data));

        try {
            kafkaTemplate.send(topicName, data);
            LOGGER.info("推送数据成功！");
        } catch (Exception e) {
            LOGGER.error(MessageFormat.format("推送数据出错，topic:{0},data:{1}"
                    , topicName, data));
        }
    }
}
