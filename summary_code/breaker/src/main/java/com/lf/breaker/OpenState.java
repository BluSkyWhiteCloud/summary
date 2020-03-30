/*
 * Copyright (c) 2001-2020 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.lf.breaker;

import com.lf.breaker.AbstractBreakerState;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 熔断器断开状态
 * 断开状态内部维护一个计数器，如果断开达到一定的时间，则自动切换到半断开状态，并且，在断开状态下，如果需要执行操作，则直接抛出异常。
 * @author liufeng
 * @version V1.0
 * @since 2020-03-10 11:06
 */
public class OpenState extends AbstractBreakerState {

    ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);

    public OpenState(BreakerManager manager) {
        super(manager);

        executorService.schedule(new Runnable() {
            @Override
            public void run() {
                timeoutHasBeenReached();
            }
        }, manager.timeout, TimeUnit.MILLISECONDS);

//        new ScheduledThreadPoolExecutor(new Runnable(){
//            @Override
//            public void run() {
//                timeoutHasBeenReached();
//            }
//        }, 5, TimeUnit.SECONDS);
//
//        final Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                timeoutHasBeenReached();
//                timer.cancel();
//            }
//        }, manager.timeout);
    }

    @Override
    public void protectedCodeIsAboutToBeCalled() {
        super.protectedCodeIsAboutToBeCalled();
        throw new RuntimeException("服务已熔断，请稍等重试！");
    }

    /**
     * 断开超过设定的阈值，自动切换到半断开状态
     */
    private void timeoutHasBeenReached()
    {
        manager.moveToHalfOpenState();
    }
}
