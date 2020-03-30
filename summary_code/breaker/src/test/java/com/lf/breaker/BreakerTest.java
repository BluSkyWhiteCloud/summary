/*
 * Copyright (c) 2001-2020 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.lf.breaker;

/**
 * TODO
 *
 * @author liufeng
 * @version V1.0
 * @since 2020-03-10 11:11
 */
public class BreakerTest {

    public static void main(String[] args) {

        //定义熔断器，失败10次进入断开状态
        //在半断开状态下，连续成功15次，进入闭合状态
        //5秒后进入半断开状态
        BreakerManager manager = new BreakerManager(10, 15, 5000);
        showState(manager);

        //模拟失败10次调用
        manager.attemptCall(false, 10);
        System.out.println(manager.failureCount);
        showState(manager);

        //这里如果再调用一次服务，正常会抛出“服务已熔断”的异常
        //manager.attemptCall(true, 1);

        long startTime = System.currentTimeMillis();
        long endTime;
        //等待熔断器超时，从Open转到HalfOpen
        try {
            System.out.println("等待熔断器超时（6s）。。。");
            startTime = System.currentTimeMillis();
            Thread.sleep(6000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        endTime = System.currentTimeMillis();
        System.out.println("总共耗时---->" +  (endTime - startTime) / 1000);
        showState(manager);

        //模拟成功调用15次
        manager.attemptCall(true, 10);
        //这里如果出现一次调用服务失败，熔断器会马上进入熔断状体，接下来的调用会抛出“服务已熔断”的异常
//        manager.attemptCall(false, 1);
        manager.attemptCall(true, 5);

        System.out.println(manager.consecutiveSuccessCount);
        System.out.println(manager.failureCount);
        showState(manager);

    }

    public static void showState(BreakerManager manager) {
        System.out.println("Breaker is Closed:" + manager.isClosed());
        System.out.println("Breaker is Open:" + manager.isOpen());
        System.out.println("Breaker is isHalfOpen:" + manager.isHalfOpen());
    }
}
