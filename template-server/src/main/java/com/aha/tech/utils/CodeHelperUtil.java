package com.aha.tech.utils;

import com.github.pagehelper.PageInterceptor;

import java.util.Properties;

/**
 * @Author: monkey
 * @Date: 2018/7/28
 * 将一些公共的代码,不易区分边界,又重复使用的代码聚合在此
 * 方便复用
 */
public class CodeHelperUtil {

    /**
     * mybatis 分页插件
     * readwrite,read库使用
     * @return
     */
    public static PageInterceptor pagePlugin() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        pageInterceptor.setProperties(properties);

        return pageInterceptor;
    }
}
