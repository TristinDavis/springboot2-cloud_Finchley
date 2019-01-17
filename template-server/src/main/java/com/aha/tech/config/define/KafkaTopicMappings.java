//package com.aha.tech.config.define;
//
//import com.aha.tech.config.KafkaConfiguration;
//import org.apache.kafka.clients.admin.AdminClientConfig;
//import org.apache.kafka.clients.admin.NewTopic;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.KafkaAdmin;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @Author: luweihong
// * @Date: 2018/8/31
// */
//@Configuration
//@AutoConfigureAfter(KafkaConfiguration.class)
//@ConditionalOnProperty(name = "kafka.enable", havingValue = "on")
//public class KafkaTopicMappings {
//
//    @Value("${kafka.producer.bootstrap-servers}")
//    private String producerBootstrapServers;
//
//    /**
//     * 客户端定义admin 才能够创建topic
//     * @return
//     */
//    @Bean
//    public KafkaAdmin admin() {
//        Map<String, Object> configs = new HashMap<>();
//        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, producerBootstrapServers);
//        return new KafkaAdmin(configs);
//    }
//
//    /**
//     * NewTopic(name,numPartitions,replicationFactor)
//     * name 主题名称
//     * numPartitions 分片数
//     * replicationFactory 副本数,不能超过线上replicationFactory数量,否则程序不报错,但是kafkaserver 会报错不会创建成功
//     *
//     * 对已经有的topic不会再次执行,也不会覆盖
//     *
//     * @return
//     */
//    @Bean
//    public NewTopic topic1() {
//        return new NewTopic("foo8", 3, (short) 1);
//    }
//
//}
