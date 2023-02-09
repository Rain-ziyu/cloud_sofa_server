package asia.huayu.util;

/**
 * @author RainZiYu
 * @Date 2023/2/8
 */
public enum SystemEnums {
    VERIFICATION_CODE_ERROR("请输入正确的验证码"),
    ACCOUNT_DOES_NOT_EXIST("账号尚未被注册"),
    LOGIN_SUCCESS("登陆成功"),
    PASSWORD_ERROR("密码错误"),
    PASSWORD_IS_INVALID("密码不合法"),
    ACCOUNT_ALREADY_EXIST("账号已被注册"),
    ACCOUNT_CREATED_SUCCESSFULLY("账号注册成功"),
    FILE_UPLOADED_SUCCESSFULLY("文件上传成功"),
    FILE_UPLOAD_FAILED("文件上传失败"),
    LOG_OFF_SUCCESSFULLY("账号注销成功"),
    FAILED_TO_GENERATE_DEFAULT_AVATAR("生成默认头像失败"),
    CAPTCHA_VERIFY_FAILED("验证码验证不通过"),
    CAPTCHA_VERIFY_SUCCESSFULLY("验证码验证通过");
    public String VALUE;

    SystemEnums(String VALUE) {
        this.VALUE = VALUE;
    }
}
