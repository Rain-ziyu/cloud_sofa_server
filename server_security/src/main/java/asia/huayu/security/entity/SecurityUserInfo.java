package asia.huayu.security.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author RainZiYu
 * @Date 2023/1/11
 * 用于存放至SecurityUser中作为其内部真正的用户信息
 */
@Data
public class SecurityUserInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    private String id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
}
