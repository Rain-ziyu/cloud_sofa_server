package asia.huayu.security.util;

/**
 * @author RainZiYu
 * @Date 2023/2/9
 */
public interface SystemValue {
    Long TOKEN_EXPIRATION_TIME = 24 * 60 * 60 * 1000L;
    String LOGIN_USER = "login_user";
    String NULL_STRING = "null";
    String USER_AREA = "user_area";
    /**
     * 在线用户的权限信息   统一前缀
     */
    String ONLINE_USER_AUTH = "online_user_auth:";
}
