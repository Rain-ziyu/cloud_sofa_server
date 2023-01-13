package asia.huayu.common.exception;


/**
 * @author RainZiYu
 * 用于程序内部主动抛出异常
 */
public class ServiceProcessException extends RuntimeException {

    public ServiceProcessException(String message) {
        super(message);
    }

    public ServiceProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceProcessException(Throwable cause) {
        super(cause);
    }
}
