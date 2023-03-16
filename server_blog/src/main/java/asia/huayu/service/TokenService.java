package asia.huayu.service;

/**
 * @author RainZiYu
 * @Date 2023/3/9
 */
public interface TokenService {
    String getSystemToken();

    /**
     * 方法getUserTokenOrSystemToken作用为：
     * 如果用户登陆获取用户token 如果未登录获取系统token
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author RainZiYu
     */
    String getUserTokenOrSystemToken();
}
