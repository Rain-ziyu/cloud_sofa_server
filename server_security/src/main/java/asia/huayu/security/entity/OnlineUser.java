package asia.huayu.security.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author RainZiYu
 * @Date 2023/2/23
 */
@Data
public class OnlineUser {

    /**
     * 登录用户id
     */
    private Integer userId;
    /**
     * 登陆时间
     */
    private Date loginTime;
    /**
     * 登陆类型  即：网页登陆 单点登录
     */
    private Integer loginType;
    /**
     * 登陆的ip地址
     */
    private String ipAddress;
    /**
     * ip所在地
     */
    private String ipSource;
    /**
     * 用户登陆的浏览器类型
     */
    private String browser;
    /**
     * 用户登录的操作系统
     */
    private String os;
    private String name;
}
