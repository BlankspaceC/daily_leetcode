package com.example.study.config;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @Date 2022/7/7 15:36
 * @ClassName TopicComponent
 * @Description ToDo
 * @Author LongX
 **/
@Component
public class TopicComponent {

    @KafkaListener(topics = {"hello","hello2"})
    public void handMessage(ConsumerRecord<String, String> record){
        String topic = record.topic();
        String msg = record.value();
        System.out.println("消费者接受消息：topic-->"+topic+",msg->>"+msg);
    }

}
