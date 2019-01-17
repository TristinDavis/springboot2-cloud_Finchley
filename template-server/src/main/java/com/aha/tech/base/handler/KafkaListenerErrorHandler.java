package com.aha.tech.base.handler;

import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: luweihong
 * @Date: 2018/9/3
 * kafka 消费全局异常处理
 * 对于单个消费和批量消费 2种handler
 * 如果不需要全局配置,则注释
 */
@Component
public class KafkaListenerErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaListenerErrorHandler.class);

    public ListenerExecutionFailedException errorHandler;

    public ListenerExecutionFailedException batchErrorHandler;

    /**
     * 单挑record消费处理
     * @return
     */
    @Bean(name = "simpleRecordErrorHandler")
    public ConsumerAwareListenerErrorHandler simpleRecordErrorHandler() {
        return (m, e, c) -> {
            LOGGER.error("simple record consumer has error at {} ", Instant.now(), e);
            LOGGER.error("message is : {} ", m);
            LOGGER.error("consumer is : {} ", c);
            this.errorHandler = e;
            MessageHeaders headers = m.getHeaders();
            c.seek(new org.apache.kafka.common.TopicPartition(
                            headers.get(KafkaHeaders.RECEIVED_TOPIC, String.class),
                            headers.get(KafkaHeaders.RECEIVED_PARTITION_ID, Integer.class)),
                    headers.get(KafkaHeaders.OFFSET, Long.class));
            return null;
        };
    }

    /**
     * 批量消费错误处理
     * @return
     */
    @Bean(name = "batchRecordErrorHandler")
    public ConsumerAwareListenerErrorHandler batchRecordErrorHandler() {
        return (m, e, c) -> {
            LOGGER.error("batch record consumer has error at {} ", Instant.now(), e);
            LOGGER.error("message is : {} ", m);
            LOGGER.error("consumer is : {} ", c);
            this.batchErrorHandler = e;
            MessageHeaders headers = m.getHeaders();
            List<String> topics = headers.get(KafkaHeaders.RECEIVED_TOPIC, List.class);
            List<Integer> partitions = headers.get(KafkaHeaders.RECEIVED_PARTITION_ID, List.class);
            List<Long> offsets = headers.get(KafkaHeaders.OFFSET, List.class);
            Map<TopicPartition, Long> offsetsToReset = new HashMap<>();
            for (int i = 0; i < topics.size(); i++) {
                int index = i;
                offsetsToReset.compute(new TopicPartition(topics.get(i), partitions.get(i)),
                        (k, v) -> v == null ? offsets.get(index) : Math.min(v, offsets.get(index)));
            }
            offsetsToReset.forEach((k, v) -> c.seek(k, v));
            return null;
        };
    }
}
