package com.aha.tech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: luweihong
 * @Date: 2018/12/25
 */
@RestController
public class KafkaController {

    @Autowired(required = false)
    private KafkaTemplate kakfaTemplate;

    @GetMapping(value = "/send")
    public String send() {
        String msg = "asdasd";
        kakfaTemplate.send("foo8", msg);
        return msg;
    }

}
