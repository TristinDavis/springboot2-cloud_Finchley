package com.aha.tech;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;
import tk.mybatis.mapper.autoconfigure.MapperAutoConfiguration;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: luweihong
 * @Date: 2018/7/25
 */
@EnableScheduling
@EnableAsync
@EnableApolloConfig
@EnableEurekaClient
@EnableCircuitBreaker
@EnableFeignClients
@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication(exclude = {RedisAutoConfiguration.class, DataSourceAutoConfiguration.class, MapperAutoConfiguration.class, PageHelperAutoConfiguration.class, KafkaAutoConfiguration.class})
public class TemplateApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateApplication.class);

    public static void main(String[] args) {
        String profile = System.getProperty("spring.profiles.active");
        Assert.notNull(profile, "请指定 [-Dspring.profiles.active]");

        SpringApplication app = new SpringApplication(TemplateApplication.class);
        try {
            ConfigurableApplicationContext configurableApplicationContext = app.run(args);
            Environment env = configurableApplicationContext.getEnvironment();
            validateProfiles(env, profile);
            printServerInfo(env);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            System.exit(-1);
        }
    }

    /**
     * 校验-Dspring.profiles.active 的值是否合法
     * @param env
     */
    private static void validateProfiles(Environment env, String inputProfile) {
        Preconditions.checkNotNull(env.getActiveProfiles(), "请指定 [-Dspring.profiles.active]");
        List<String> profiles = Arrays.asList(env.getActiveProfiles());
        Preconditions.checkArgument(profiles.contains(inputProfile), "输入的-Dspring.profiles.active 与 有效的 active不符");
    }

    /**
     * 启动输出信息
     * @param env
     * @throws IOException
     */
    private static void printServerInfo(Environment env) throws IOException {
        String appBanner = StreamUtils.copyToString(new ClassPathResource("app-banner.txt").getInputStream(),
                Charset.defaultCharset());

        String applicationName = env.getProperty("spring.application.name");
        String servletContextPath = env.getProperty("server.servlet.context-path");
        String serverPort = env.getProperty("server.port");

        LOGGER.info(appBanner, applicationName,
                StringUtils.isEmpty(env.getProperty("server.ssl.key-store")) ? "http" : "https",
                env.getProperty("server.port"), StringUtils.defaultString(servletContextPath),
                StringUtils.isEmpty(env.getProperty("server.ssl.key-store")) ? "http" : "https",
                InetAddress.getLocalHost().getHostAddress(), serverPort,
                StringUtils.defaultString(servletContextPath),
                org.springframework.util.StringUtils.arrayToCommaDelimitedString(env.getActiveProfiles()),
                env.getProperty("PID"), Charset.defaultCharset());
    }

}
