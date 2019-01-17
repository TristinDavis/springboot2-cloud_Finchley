package com.aha.tech.event;

import com.aha.tech.config.RedisConfiguration;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @Author: luweihong
 * @Date: 2018/9/19
 *
 * 用于监听一些变量变更后的动作,如果无需后续动作可以不更新
 * apollo实时更新
 */
@Component
public class VariableRefreshEvent {

    private static final Logger LOGGER = LoggerFactory.getLogger(VariableRefreshEvent.class);

    @Autowired
    private RefreshScope refreshScope;

    @Autowired
    private RedisConfiguration redisConfiguration;

    /**
     * apollo 配置变更时间
     * @param changeEvent
     */
    @ApolloConfigChangeListener({"application"})
    private void onChange(ConfigChangeEvent changeEvent) {
        if (changeEvent.isChanged("private.redis.timeout") || changeEvent.isChanged("private.redis.max-wait")) {
            LOGGER.info("before refresh {}", redisConfiguration.privateRedisTimeout);
            LOGGER.info("after refresh {}", redisConfiguration.privateRedisTimeout);
            refreshScope.refreshAll();
        }
    }
}
