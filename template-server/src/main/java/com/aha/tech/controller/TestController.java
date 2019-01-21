package com.aha.tech.controller;

import com.aha.tech.base.commons.response.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.aha.tech.message.producer.KafkaProducer;

/**
 * @Author: luweihong
 * @Date: 2018/7/25
 *
 * 用作单元测试,可以删除
 */
@RestController
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);


    /**
     * 测试 rest的get请求是否能正确接收参数
     * 对应 restTemplateTest.java 的 get test
     * @return
     */
    @GetMapping(value = "/test")
    public RpcResponse<Long> restGet()  {
        Object a = null;
        a.toString();
        return new RpcResponse<>();
    }

}
