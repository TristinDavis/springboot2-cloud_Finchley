package com.aha.tech.config;

import com.aha.tech.commons.utils.DateUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.data.elasticsearch.core.DefaultEntityMapper;
import org.springframework.data.elasticsearch.core.geo.CustomGeoModule;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * @Author: luweihong
 * @Date: 2018/8/24
 *
 * 自定义es插入和解析使用jackson的配置
 */
public class ElasticsearchJacksonMapper extends DefaultEntityMapper {

    private ObjectMapper objectMapper;

    public ElasticsearchJacksonMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.setTimeZone(DateUtil.DEFAULT_TIME_ZONE);
        objectMapper.setDateFormat(new SimpleDateFormat(DateUtil.DATE_FORMAT_FULL));
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.registerModule(new CustomGeoModule());
    }

    @Override
    public String mapToString(Object object) throws IOException {
        return objectMapper.writeValueAsString(object);
    }

    @Override
    public <T> T mapToObject(String source, Class<T> clazz) throws IOException {
        return objectMapper.readValue(source, clazz);
    }
}
