package com.huayu.listener;

import com.huayu.entity.ExceptionLog;
import com.huayu.entity.OperationLog;
import com.huayu.event.ExceptionLogEvent;
import com.huayu.event.OperationLogEvent;
import com.huayu.mapper.ExceptionLogMapper;
import com.huayu.mapper.OperationLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
/**
 * @author User
 * 异步监听器   当发生指定类的事件时进行日志记录
 */
@Component
public class AuroraListener {

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Autowired
    private ExceptionLogMapper exceptionLogMapper;

    @Async
    @EventListener(OperationLogEvent.class)
    public void saveOperationLog(OperationLogEvent operationLogEvent) {
        operationLogMapper.insert((OperationLog) operationLogEvent.getSource());
    }

    @Async
    @EventListener(ExceptionLogEvent.class)
    public void saveExceptionLog(ExceptionLogEvent exceptionLogEvent) {
        exceptionLogMapper.insert((ExceptionLog) exceptionLogEvent.getSource());
    }

}
