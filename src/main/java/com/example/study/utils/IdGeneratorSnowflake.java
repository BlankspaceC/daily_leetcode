package com.example.study.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Comparator;

/**
 * @Author: LongX
 * @Date: 2022/8/16 13:21
 * @Description: TODO
 * @Version: 1.0
 **/


@Slf4j
@Component
public class IdGeneratorSnowflake {
    private long workerId = 0;
    private long datacenterId = 3;
    private Snowflake snowflake = IdUtil.createSnowflake(workerId, datacenterId);

    @PostConstruct
    public void init() {
        try {
            workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
            log.info("当前机器的workerId:{}", workerId);
        } catch (Exception e) {
            log.info("当前机器的workerId获取失败", e);
            workerId = NetUtil.getLocalhostStr().hashCode();
            log.info("当前机器 workId:{}", workerId);
        }

    }

    public synchronized long snowflakeId() {
        return snowflake.nextId();
    }

    public synchronized long snowflakeId(long workerId, long datacenterId) {
        snowflake = IdUtil.createSnowflake(workerId, datacenterId);
        return snowflake.nextId();
    }


    public static void main(String[] args) {
        IdGeneratorSnowflake generator=new IdGeneratorSnowflake();
        new Thread(()->{
            String name=Thread.currentThread().getName();
            for (int i = 0; i <50 ; i++) {
                System.out.println(name+":"+Long.toBinaryString(generator.snowflakeId()));
            }
        }).start();
        new Thread(()->{
            String name=Thread.currentThread().getName();
            for (int i = 0; i <50 ; i++) {
                System.out.println(name+":"+Long.toBinaryString(generator.snowflakeId()));
            }
        }).start();
    }
}


