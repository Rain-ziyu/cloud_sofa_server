package asia.huayu.listener;

import asia.huayu.entity.ExceptionLog;
import asia.huayu.entity.OperationLog;
import asia.huayu.event.ExceptionLogEvent;
import asia.huayu.event.OperationLogEvent;
import asia.huayu.mapper.ExceptionLogMapper;
import asia.huayu.mapper.OperationLogMapper;
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
