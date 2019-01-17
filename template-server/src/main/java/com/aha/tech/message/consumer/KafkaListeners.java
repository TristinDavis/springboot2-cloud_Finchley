package com.aha.tech.message.consumer;

import com.aha.tech.message.producer.KafkaProducer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: luweihong
 * @Date: 2018/8/31
 */
@Component
public class KafkaListeners {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);


    /**
     *
     *  @see com.aha.tech.config.KafkaConfiguration
     *  Acknowledgment or BatchAcknowledgment
     * @param records
     */
    @KafkaListener(topics = "foo8", containerFactory = "batchContainerFactory", groupId = "b")
    public void receive(List<ConsumerRecord<String, Object>> records, Acknowledgment ack) {
        LOGGER.info("meta info : {}", records);

        for (ConsumerRecord record : records) {
            LOGGER.info("消费的分区名 : {} ", record.partition());
            LOGGER.info("消费的offset : {} ", record.offset());
            LOGGER.info("消息的创建时间 : {} ", record.timestamp());
            LOGGER.info("payload 的 header info : {} ", record.headers());
            LOGGER.info("payload content : {} ", record.value());
        }

        ack.acknowledge();
    }

}