package com.aha.tech.config.mybatis.readwrite;

import com.aha.tech.utils.CodeHelperUtil;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.annotation.Resource;
import javax.sql.DataSource;

import static com.aha.tech.base.constants.ApplicationConstants.SCAN_BASE_REPOSITORY_DAO_READ_WRITE_PACKAGE;
import static com.aha.tech.base.constants.ApplicationConstants.SCAN_READWRITE_MAPPER_XML_PATH;

/**
 * @Author: luweihong
 * @Date: 2018/7/27
 *
 */
@Configuration
@ConditionalOnProperty(name = "readwritedb.enable", havingValue = "on")
@AutoConfigureAfter(ReadWriteJdbcConfiguration.class)
@MapperScan(basePackages = {SCAN_BASE_REPOSITORY_DAO_READ_WRITE_PACKAGE}, sqlSessionFactoryRef = "readwriteSqlSessionFactory")
public class ReadWriteMybatisConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadWriteMybatisConfiguration.class);

    @Resource
    private DataSource readwriteDataSource;

    @Primary
    @Qualifier("readwriteSqlSessionFactory")
    @Bean(name = "readwriteSqlSessionFactory")
    public SqlSessionFactory readwriteSqlSessionFactory() throws Exception {
        LOGGER.info("readwriteSqlSessionFactory init completed !");
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(readwriteDataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(SCAN_READWRITE_MAPPER_XML_PATH));
        bean.setPlugins(new Interceptor[]{CodeHelperUtil.pagePlugin()});
        return bean.getObject();
    }

    @Primary
    @Qualifier("readwriteSqlSessionTemplate")
    @Bean(name = "readwriteSqlSessionTemplate")
    public SqlSessionTemplate readwriteSqlSessionTemplate(@Qualifier("readwriteSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 使用读写库的事务
     * 直接@Transactional即可
     * @return
     */
    @Primary
    @Qualifier("readwriteTransactionManager")
    @Bean(name = "readwriteTransactionManager")
    public DataSourceTransactionManager readwriteTransactionManager() {
        return new DataSourceTransactionManager(readwriteDataSource);
    }

}
