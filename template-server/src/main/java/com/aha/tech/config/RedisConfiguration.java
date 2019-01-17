package com.aha.tech.config;

import com.aha.tech.base.serializer.FastJsonSerializer;
import com.alibaba.fastjson.parser.ParserConfig;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;
import java.net.UnknownHostException;

/**
 * @Author: luweihong
 * @Date: 2018/7/25
 */
@Configuration
public class RedisConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisConfiguration.class);

    @Value("${public.redis.host:localhost}")
    public String publicRedisHost;

    @Value("${public.redis.port:6379}")
    public Integer publicRedisPort;

    @Value("${public.redis.password}")
    public String publicRedisPassword;

    @Value("${public.redis.timeout:10000}")
    public Integer publicRedisTimeout;

    @Value("${public.redis.database:0}")
    public Integer publicRedisDatabase;

    @Value("${public.redis.max-active:50}")
    public Integer publicRedisMaxActive;

    @Value("${public.redis.max-idle:10}")
    public Integer publicRedisMaxWait;

    @Value("${public.redis.max-idle:15}")
    public Integer publicRedisMaxIdle;

    @Value("${public.redis.min-idle:5}")
    public Integer publicRedisMinIdle;

    // ------ 私有redis配置  ------ //

    @Value("${private.redis.host:localhost}")
    public String privateRedisHost;

    @Value("${private.redis.port:6379}")
    public Integer privateRedisPort;

    @Value("${private.redis.password}")
    public String privateRedisPassword;

    @Value("${private.redis.timeout:10000}")
    public Integer privateRedisTimeout;

    @Value("${private.redis.database:0}")
    public Integer privateRedisDatabase;

    @Value("${private.redis.max-active:50}")
    public Integer privateRedisMaxActive;

    @Value("${private.redis.max-idle:10}")
    public Integer privateRedisMaxWait;

    @Value("${private.redis.max-idle:15}")
    public Integer privateRedisMaxIdle;

    @Value("${private.redis.min-idle:5}")
    public Integer privateRedisMinIdle;


    /**
     * 公用redis factory
     * @return
     * @throws UnknownHostException
     */
    @Bean(name = "publicRedisConnectionFactory")
    public LettuceConnectionFactory publicRedisConnectionFactory() {
        LettuceClientConfiguration clientConfig = lettuceClientConfiguration(publicRedisMaxActive, publicRedisMaxIdle, publicRedisMinIdle, publicRedisMaxWait);
        RedisStandaloneConfiguration redisStandaloneConfiguration = redisStandaloneConfiguration(publicRedisHost, publicRedisPort, publicRedisPassword, publicRedisDatabase);
        return createLettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
    }

    /**
     * 私有的redis factory
     * @return
     */
    @Primary
    @Bean("privateRedisConnectionFactory")
    public LettuceConnectionFactory privateRedisConnectionFactory() {
        LettuceClientConfiguration clientConfig = lettuceClientConfiguration(privateRedisMaxActive, privateRedisMaxIdle, privateRedisMinIdle, privateRedisMaxWait);
        RedisStandaloneConfiguration redisStandaloneConfiguration = redisStandaloneConfiguration(privateRedisHost, privateRedisPort, privateRedisPassword, privateRedisDatabase);
        return createLettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
    }

    /**
     * 构建lettuceClientConfiguration
     * @param maxActive
     * @param maxIdle
     * @param minIdle
     * @param maxWait
     * @return
     */
    private LettuceClientConfiguration lettuceClientConfiguration(Integer maxActive, Integer maxIdle, Integer minIdle, Integer maxWait) {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(maxActive);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setMaxWaitMillis(maxWait);
        LettuceClientConfiguration.LettuceClientConfigurationBuilder builder = LettucePoolingClientConfiguration.builder()
                .poolConfig(config);
        LettuceClientConfiguration clientConfig = builder.build();
        return clientConfig;
    }

    /**
     * 构建redis config
     * @param host
     * @param port
     * @param password
     * @param database
     * @return
     */
    private RedisStandaloneConfiguration redisStandaloneConfiguration(String host, Integer port, String password, Integer database) {
        LOGGER.info(String.format("初始化公用redis ----> url : %s:%s,使用database [%s]", host, port, database));
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        config.setPassword(RedisPassword.of(password));
        config.setDatabase(database);
        return config;
    }


    /**
     * 构建redis工厂
     * @param redisStandaloneConfiguration
     * @param clientConfiguration
     * @return
     */
    private LettuceConnectionFactory createLettuceConnectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration, LettuceClientConfiguration clientConfiguration) {
//        if (getSentinelConfig() != null) {
//            return new LettuceConnectionFactory(getSentinelConfig(), clientConfiguration);
//        }
//        if (getClusterConfiguration() != null) {
//            return new LettuceConnectionFactory(getClusterConfiguration(),
//                    clientConfiguration);
//        }
        return new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfiguration);
    }

    /**
     * 自定义redis序列化 使用fast json
     * 和前端共用的redis配置需要各个业务方自行去前端沟通序列化策略
     * 后端统一使用fastjson
     * @return
     */
    @Bean(name = "publicRedisTemplate")
    public RedisTemplate<String, Object> publicRedisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(new FastJsonSerializer(Object.class));
//        template.setHashKeySerializer(new StringRedisSerializer());
//        template.setHashValueSerializer(new FastJsonSerializer(Object.class));
//        template.setEnableDefaultSerializer(Boolean.TRUE);
        template.setConnectionFactory(publicRedisConnectionFactory());

        return template;
    }

    /**
     * 自定义redis序列化 使用fast json
     * @return
     */
    @Primary
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> privateRedisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        FastJsonSerializer<Object> fastJsonSerializer = new FastJsonSerializer<>(Object.class);
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(fastJsonSerializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(fastJsonSerializer);
        template.setDefaultSerializer(fastJsonSerializer);
        template.setEnableTransactionSupport(Boolean.TRUE);
//        template.setEnableDefaultSerializer(false);
        template.setConnectionFactory(privateRedisConnectionFactory());
        template.afterPropertiesSet();

        return template;
    }


}
