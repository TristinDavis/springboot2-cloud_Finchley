//package com.aha.tech.config.define;
//
//import com.aha.tech.config.ElasticsearchConfiguration;
//import com.aha.tech.model.entity.ProductEntity;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
//
///**
// * @Author: luweihong
// * @Date: 2018/8/27
// */
//@Configuration
//@AutoConfigureAfter(ElasticsearchConfiguration.class)
//@ConditionalOnProperty(name = "es.enable", havingValue = "on")
//public class ElasticsearchMappings implements CommandLineRunner {
//
//    @Autowired
//    private ElasticsearchTemplate elasticsearchTemplate;
//
//    @Override
//    public void run(String... args) {
//        if (!elasticsearchTemplate.indexExists(ProductEntity.class)) {
//            elasticsearchTemplate.createIndex(ProductEntity.class);
//        }
//
//        elasticsearchTemplate.putMapping(ProductEntity.class);
//    }
//
//}
