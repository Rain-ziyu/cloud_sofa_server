package com.huayu.event;

import com.huayu.entity.OperationLog;
import org.springframework.context.ApplicationEvent;

public class OperationLogEvent extends ApplicationEvent {

    public OperationLogEvent(OperationLog operationLog) {
        super(operationLog);
    }
}
