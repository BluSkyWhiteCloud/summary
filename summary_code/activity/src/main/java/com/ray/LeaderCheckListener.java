/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.ray;

import java.util.List;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO
 *
 * @author liufeng
 * @version V1.0
 * @since 2019-10-16 16:48
 */
public class LeaderCheckListener implements TaskListener {

    private static final long serialVersionUID = 4285398130708457006L;

    /**
     * 日志记录Logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(LeaderCheckListener.class);

    @Override
    public void notify(DelegateTask task) {
        LOG.info("领导审核监听器...");
        //设置任务处理候选人
        UserService userService = SpringUtil.getBean(UserService.class);
        List<String> leaders = userService.getSimpleCheckerByDept(Long.valueOf(task.getVariable("dept").toString()));
        LOG.info(leaders.toString());
        LOG.info(task.getVariable("dept").toString());
        task.addCandidateUsers(leaders);
    }
}
