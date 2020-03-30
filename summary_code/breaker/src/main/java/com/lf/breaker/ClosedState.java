/*
 * Copyright (c) 2001-2020 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.lf.breaker;

import com.lf.breaker.AbstractBreakerState;
import com.lf.breaker.BreakerManager;

/**
 * 熔断器闭合状态
 * 在闭合状态下，如果发生错误，并且错误次数达到阈值，则状态机切换到断开状态
 * @author liufeng
 * @version V1.0
 * @since 2020-03-10 11:05
 */
public class ClosedState extends AbstractBreakerState {

    public ClosedState(BreakerManager manager) {
        super(manager);

        //重置失败计数器
        manager.resetFailureCount();
    }

    @Override
    public void ActUponException() {
        super.ActUponException();

        //如果失败次数达到阈值，则切换到断开状态
        if (manager.failureThresholdReached()) {
            manager.moveToOpenState();
        }
    }
}
