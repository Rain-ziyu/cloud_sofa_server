package asia.huayu.security.util;

/**
 * @author RainZiYu
 * @Date 2023/2/8
 */
public enum SystemEnums {

    LOGIN_SUCCESS("登陆成功"),
    PASSWORD_ERROR("账号或密码错误"),
    LOGIN_ERROR("登录异常，请稍后重试"),
    // 请求头中携带token的key
    AUTH_NAME("token"),
    TOKEN_ERROR("token无效"),
    NO_AUTH("无权限");
    public String VALUE;

    SystemEnums(String VALUE) {
        this.VALUE = VALUE;
    }
}
