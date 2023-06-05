package com.example.study.controller;


import com.example.study.config.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: LongX
 * @Date: 2022/8/16 8:54
 * @Description: TODO
 * @Version: 1.0
 **/
@RestController
public class TestController {

    @Autowired
    KafkaProducer kafkaProducer;

    @GetMapping("/test")
    public String test(){
        kafkaProducer.sendMessage("aaaa");
        return "aaaa";
    }
}
