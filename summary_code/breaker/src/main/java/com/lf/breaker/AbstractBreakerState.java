/*
 * Copyright (c) 2001-2020 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.lf.breaker;

/**
 * 熔断器状态转移操作的抽象类
 *
 * @author liufeng
 * @version V1.0
 * @since 2020-03-10 11:03
 */
public abstract class AbstractBreakerState {

    protected BreakerManager manager;

    public AbstractBreakerState(BreakerManager manager) {
        this.manager = manager;
    }

    /**
     * 调用方法之前处理的操作
     */
    public void protectedCodeIsAboutToBeCalled() {
        //如果是断开状态，直接返回，然后等超时转换到半断开状态
        if (manager.isOpen()) {
            throw new RuntimeException("服务已熔断，请稍等重试！");
        }
    }

    /**
     * 方法调用成功之后的操作
     */
    public void protectedCodeHasBeenCalled() {
        manager.increaseSuccessCount();
    }

    /**
     * 方法调用发生异常操作后的操作
     */
    public void ActUponException() {
        //增加失败次数计数器，并且保存错误信息
        manager.increaseFailureCount();
        //重置连续成功次数
        manager.resetConsecutiveSuccessCount();
    }
}
