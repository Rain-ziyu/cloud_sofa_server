package asia.huayu.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author RainZiYu
 * @Date 2023/2/9
 */
@Data
public class CircleCaptchaDTO {
    String uuid;
    String imgBase64;
    @NotBlank(message = "用户未输入验证码")
    String userInput;
}
