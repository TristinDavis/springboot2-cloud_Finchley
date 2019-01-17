package com.aha.tech.config.mybatis.readwrite;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @Author: luweihong
 * @Date: 2018/7/26
 *
 */
@Configuration
@ConditionalOnProperty(name = "readwritedb.enable", havingValue = "on")
public class ReadWriteJdbcConfiguration {

    public static final Logger LOG = LoggerFactory.getLogger(ReadWriteJdbcConfiguration.class);

    @Value("${jdbc.readwrite.driverClassName}")
    private String driverClassName;

    @Value("${jdbc.readwrite.jdbcUrl}")
    private String jdbcUrl;

    @Value("${jdbc.readwrite.username}")
    private String username;

    @Value("${jdbc.readwrite.password}")
    private String password;

    @Value("${jdbc.readwrite.connectionTimeout}")
    private Long connectionTimeout;

    @Value("${jdbc.readwrite.idleTimeout}")
    private Long idleTimeout;

    @Value("${jdbc.readwrite.maximumPoolSize}")
    private Integer maximumPoolSize;

    @Value("${jdbc.readwrite.minimumIdle}")
    private Integer minimumIdle;

    @Primary
    @Qualifier("readwriteDataSource")
    @Bean(name = "readwriteDataSource")
    public DataSource readwriteDataSource() {
        LOG.info("============= begin init readwrite datasource params : {} =============", this);
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(driverClassName);
        hikariDataSource.setJdbcUrl(jdbcUrl);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);
        hikariDataSource.setConnectionTimeout(connectionTimeout);
        hikariDataSource.setIdleTimeout(idleTimeout);
        hikariDataSource.setMaximumPoolSize(maximumPoolSize);
        hikariDataSource.setMinimumIdle(minimumIdle);
        hikariDataSource.setPoolName("read-write-pool");
        LOG.info("============= readwrite datasource init completed ! =============");
        return hikariDataSource;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }
}
