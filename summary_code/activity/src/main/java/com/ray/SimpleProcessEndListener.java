/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.ray;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO
 *
 * @author liufeng
 * @version V1.0
 * @since 2019-10-16 16:53
 */
public class SimpleProcessEndListener implements ExecutionListener {
    private static final long serialVersionUID = 5212042435691138021L;

    /**
     * 日志记录Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(SimpleProcessEndListener.class);

    @Override
    public void notify(DelegateExecution arg0) throws Exception {
        LOG.info("流程结束监听器...");
        //TODO 修改业务数据状态
    }
}
