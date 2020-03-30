/*
 * Copyright (c) 2001-2020 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.lf.kafka.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * TODO
 *
 * @author liufeng
 * @version V1.0
 * @since 2020-02-25 12:12
 */
@SpringBootApplication
@EnableScheduling
public class KafkaApplication {
    @Autowired
    KafkaSender kafkaSender;
    @Autowired
    KafkaConsumer kafkaConsumer;
    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);
    }
    @Scheduled(cron = "0/2 * * * * ? ")
    public void sendMsg(){
        kafkaSender.sendTest();
        kafkaSender.send();
    }
}
