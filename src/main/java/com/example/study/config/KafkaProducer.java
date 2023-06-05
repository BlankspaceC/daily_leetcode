package com.example.study.config;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @Date 2022/7/7 15:07
 * @ClassName kafkaProducer
 * @Description ToDo
 * @Author LongX
 **/
@Component
public class KafkaProducer {
    /**
     * KafkaTemplate泛型类型跟消费者ConsumerRecord的泛型类型保持一致
     */
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
    /**
     * 发送消息
     */
    public void sendMessage(String message) {
        if(StringUtils.isEmpty(message))
            return;
        try{
            ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send("hello","hello", message);
            listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                @Override
                public void onSuccess(SendResult<String, String> result) {
                    System.out.println("sendMessage success");
                }
                @Override
                public void onFailure(Throwable ex) {
                    System.out.println("sendMessage error");
                }
            });
        }catch (Exception e){
            System.out.println("sendMessage exception");
        }
    }
}