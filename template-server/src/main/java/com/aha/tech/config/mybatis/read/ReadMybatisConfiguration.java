package com.aha.tech.config.mybatis.read;

import com.aha.tech.utils.CodeHelperUtil;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import tk.mybatis.spring.annotation.MapperScan;

import javax.annotation.Resource;
import javax.sql.DataSource;

import static com.aha.tech.base.constants.ApplicationConstants.SCAN_BASE_REPOSITORY_DAO_READ_PACKAGE;
import static com.aha.tech.base.constants.ApplicationConstants.SCAN_READ_MAPPER_XML_PATH;

/**
 * @Author: luweihong
 * @Date: 2018/7/27
 */
@Configuration
@ConditionalOnProperty(name = "readdb.enable", havingValue = "on")
@AutoConfigureAfter(ReadJdbcConfiguration.class)
@MapperScan(basePackages = {SCAN_BASE_REPOSITORY_DAO_READ_PACKAGE}, sqlSessionFactoryRef = "readSqlSessionFactory")
public class ReadMybatisConfiguration {

    @Resource
    private DataSource readDataSource;

    @Qualifier("readSqlSessionFactory")
    @Bean(name = "readSqlSessionFactory")
    public SqlSessionFactory readSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(readDataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(SCAN_READ_MAPPER_XML_PATH));
        bean.setPlugins(new Interceptor[]{CodeHelperUtil.pagePlugin()});

        return bean.getObject();
    }

    @Qualifier("readSqlSessionTemplate")
    @Bean(name = "readSqlSessionTemplate")
    public SqlSessionTemplate readSqlSessionTemplate(@Qualifier("readSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
