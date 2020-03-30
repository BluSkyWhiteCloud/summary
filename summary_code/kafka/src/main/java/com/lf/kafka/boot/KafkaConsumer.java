/*
 * Copyright (c) 2001-2020 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.lf.kafka.boot;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author liufeng
 * @version V1.0
 * @since 2020-02-25 12:10
 */
@Component
@Slf4j
public class KafkaConsumer {

    /**
     * 监听test主题,有消息就读取
     * @param message
     */
    @KafkaListener(topics = {"hello", "hello12"})
    public void consumer(String message) {
        log.info("c1 消费了hello topic messge:{}", message);
        Map map = new HashMap();
        map.put(null, 1);
        Map synMap = Collections.synchronizedMap(map);
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        concurrentHashMap.put(null, 1);
    }

//    @KafkaListener(topics = {"hello12"})
//    public void consumerB(String message) {
//        log.info("c2 hello12 topic messge:{}", message);
//    }
}
