//package com.aha.tech.config;
//
//import com.alibaba.dubbo.config.*;
//import com.alibaba.dubbo.rpc.cluster.loadbalance.RoundRobinLoadBalance;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @Author: luweihong
// * @Date: 2018/12/10
// */
//@Configuration
//public class DubboConfiguration {
//
//    @Value("${spi.application.name}")
//    private String registerApplicationName;
//
//    @Value("${spi.provider.timeout}")
//    private Integer providerTimeout;
//
//    @Value("${spi.consumer.timeout}")
//    private Integer consumerTimeout;
//
//    @Value("${spi.provider.serialization}")
//    private String serialization;
//
//    @Value("${spi.provider.thread.num}")
//    private Integer threadNum;
//
//    @Value("${spi.provider.thread.pool}")
//    private String threadPool;
//
//    @Value("${spi.provider.dispatcher}")
//    private String dispatcher;
//
//    @Value("${spi.registry.address}")
//    private String registryAddress;
//
//    @Value("${spi.registry.protocol}")
//    private String registryProtocol;
//
//    @Value("${spi.protocol.name}")
//    private String protocolName;
//
//    @Value("${spi.protocol.port}")
//    private Integer protocolPort;
//
//    @Value("${spi.server.group:ahaschool}")
//    private String group;
//
//    /**
//     * 应用级别的配置
//     * 对应xml中 <dubbo:application > </dubbo:application> 配置
//     * @return
//     */
//    @Bean
//    public ApplicationConfig applicationConfig() {
//        ApplicationConfig applicationConfig = new ApplicationConfig();
//        applicationConfig.setName(registerApplicationName);
//        applicationConfig.setId(registerApplicationName);
//        return applicationConfig;
//    }
//
//    /**
//     * 服务暴露方配置
//     * 服务方要尽可能多的配置
//     * 当消费方缺失配置,服务方配置生效
//     *
//     * 配置优先级 方法 > 消费者 > 提供者 > 全局
//     * 即scope小的优先级高
//     *
//     * dispatcher :
//     * all: All messages will be dispatched to thread pool, including request, response, connect event, disconnect event and heartbeat.
//     * direct: All messages will not be dispatched to thread pool and will be executed directly by I/O thread.
//     * message: Only request, response messages will be dispatched to I/O thread. Other messages like disconnect, connect, heartbeat messages will be executed by I/O thread.
//     * execution: Only request message will be dispatched to thread pool. Other messages like response, connect, disconnect, heartbeat will be directly executed by I/O thread.
//     * connection: I/O thread will put disconnect and connect events in the queue and execute them sequentially, other messages will be dispatched to the thread pool.
//     *
//     * @return
//     */
//    @Bean
//    public ProviderConfig providerConfig() {
//        ProviderConfig providerConfig = new ProviderConfig();
//        providerConfig.setTimeout(providerTimeout);
//        providerConfig.setGroup(group);
//
//        return providerConfig;
//    }
//
//    @Bean
//    public ConsumerConfig consumerConfig() {
//        ConsumerConfig consumerConfig = new ConsumerConfig();
//        consumerConfig.setTimeout(consumerTimeout);
//        consumerConfig.setCheck(Boolean.FALSE);
//        consumerConfig.setLoadbalance(RoundRobinLoadBalance.NAME);
//        consumerConfig.setGroup(group);
//        consumerConfig.setRetries(Integer.SIZE);
//        return consumerConfig;
//    }
//
//    /**
//     * 注册属性配置
//     * 对应 <dubbo:registry></dubbo:registry> 的配置
//     * @return
//     */
//    @Bean
//    public RegistryConfig registryConfig() {
//        RegistryConfig registryConfig = new RegistryConfig();
//        registryConfig.setProtocol(registryProtocol);
//        registryConfig.setAddress(registryAddress);
//        registryConfig.setGroup(group);
//        return registryConfig;
//    }
//
//    /**
//     *
//     * dubbo协议的配置
//     * 对应xml <dubbo:protocol></dubbo:protocol> 的配置
//     * 如果provider 或者 consumer 没有配置,则会默认使用protocol的公有配置
//     * 例如序列化,线程池类型,数量,dispatcher等
//     * @return
//     */
//    @Bean
//    public ProtocolConfig protocolConfig() {
//        ProtocolConfig protocolConfig = new ProtocolConfig();
//        protocolConfig.setName(protocolName);
//        protocolConfig.setPort(protocolPort);
//        protocolConfig.setSerialization(serialization);
//        protocolConfig.setThreadpool(threadPool);
//        protocolConfig.setDispatcher(dispatcher);
//        protocolConfig.setThreads(threadNum);
//        return protocolConfig;
//    }
//}
