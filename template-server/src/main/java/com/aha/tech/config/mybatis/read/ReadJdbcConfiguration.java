package com.aha.tech.config.mybatis.read;

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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

/**
 * @Author: luweihong
 * @Date: 2018/7/26
 *
 */
@Configuration
@ConditionalOnProperty(name = "readdb.enable", havingValue = "on")
public class ReadJdbcConfiguration implements TransactionManagementConfigurer {

    public static final Logger LOG = LoggerFactory.getLogger(ReadJdbcConfiguration.class);

    @Value("${jdbc.read.driverClassName}")
    private String driverClassName;

    @Value("${jdbc.read.jdbcUrl}")
    private String jdbcUrl;

    @Value("${jdbc.read.username}")
    private String username;

    @Value("${jdbc.read.password}")
    private String password;

    @Value("${jdbc.read.connectionTimeout}")
    private Long connectionTimeout;

    @Value("${jdbc.read.idleTimeout}")
    private Long idleTimeout;

    @Value("${jdbc.read.maximumPoolSize}")
    private Integer maximumPoolSize;

    @Value("${jdbc.read.minimumIdle}")
    private Integer minimumIdle;

    @Qualifier("readDataSource")
    @Bean(name = "readDataSource")
    public DataSource readDataSource() {
        LOG.info("============= begin init read datasource params : {} =============", this);
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(driverClassName);
        hikariDataSource.setJdbcUrl(jdbcUrl);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);
        hikariDataSource.setConnectionTimeout(connectionTimeout);
        hikariDataSource.setIdleTimeout(idleTimeout);
        hikariDataSource.setMaximumPoolSize(maximumPoolSize);
        hikariDataSource.setMinimumIdle(minimumIdle);
        hikariDataSource.setPoolName("read-pool");
        LOG.info("============= read datasource init completed ! =============");
        return hikariDataSource;
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return readTransactionManager();
    }

    /**
     * 使用读库的事务
     * 直接@Transactional即可
     * @return
     */
    @Bean(name = "readTransactionManager")
    public DataSourceTransactionManager readTransactionManager() {
        return new DataSourceTransactionManager(readDataSource());
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }
}
