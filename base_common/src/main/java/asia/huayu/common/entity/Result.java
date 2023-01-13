package asia.huayu.common.entity;


import lombok.Data;

import java.io.Serializable;

/**
 * 接口返回数据格式
 *
 * @author wwl
 */
@Data

public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 成功标志
     */

    private boolean success = true;

    /**
     * 返回处理消息
     */

    private String message = "";


    /**
     * 返回代码
     */

    private Integer result = 0;

    /**
     * 返回数据对象 data
     */

    private T data;

    /**
     * 时间戳
     */
    private long timestamp = System.currentTimeMillis();

    public Result() {
    }


    public Result(Integer code, String message) {
        this.result = code;
        this.message = message;
    }

    public static <T> Result<T> SUCCESS(String message) {
        Result<T> r = new Result<T>();
        r.message = message;
        r.result = 200;
        r.success = true;
        return r;
    }

    public static <T> Result<T> OK() {
        Result<T> r = new Result<T>();
        r.setSuccess(true);
        r.setResult(200);
        return r;
    }

    /**
     * 此方法是为了兼容升级所创建
     *
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Result<T> OK(String msg) {
        Result<T> r = new Result<T>();
        r.setSuccess(true);
        r.setResult(0);
        r.setMessage(msg);
        // Result OK(String msg)方法会造成兼容性问题 issues/I4IP3D
        r.setData((T) msg);
        return r;
    }

    public static <T> Result<T> OK(T data) {
        Result<T> r = new Result<T>();
        r.setSuccess(true);
        r.setResult(0);
        r.setData(data);
        return r;
    }

    public static <T> Result<T> OK(String msg, T data) {
        Result<T> r = new Result<T>();
        r.setSuccess(true);
        r.setResult(0);
        r.setMessage(msg);
        r.setData(data);
        return r;
    }

    public static <T> Result<T> ERROR(String msg, T data) {
        Result<T> r = new Result<T>();
        r.setSuccess(false);
        r.setResult(1);
        r.setMessage(msg);
        r.setData(data);
        return r;
    }

    public static <T> Result<T> ERROR(String msg) {
        return ERROR(1, msg);
    }

    public static <T> Result<T> ERROR(int code, String msg) {
        Result<T> r = new Result<T>();
        r.setResult(code);
        r.setMessage(msg);
        r.setSuccess(false);
        return r;
    }

    /**
     * 无权限访问返回结果
     */
    public static <T> Result<T> noauth(String msg) {
        return ERROR(510, msg);
    }

    public Result<T> ERROR500(String message) {
        this.message = message;
        this.result = 1;
        this.success = false;
        return this;
    }


}
