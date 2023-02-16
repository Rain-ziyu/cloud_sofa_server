package asia.huayu.event;

import asia.huayu.entity.ExceptionLog;
import org.springframework.context.ApplicationEvent;

public class ExceptionLogEvent extends ApplicationEvent {
    public ExceptionLogEvent(ExceptionLog exceptionLog) {
        super(exceptionLog);
    }
}
