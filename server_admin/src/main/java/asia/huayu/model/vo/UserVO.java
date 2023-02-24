package asia.huayu.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户账号")
public class UserVO {

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @Schema(description = "username", title = "用户名", required = true, type = "String")
    private String username;

    @Size(min = 6, message = "密码不能少于6位")
    @NotBlank(message = "密码不能为空")
    @Schema(description = "password", title = "密码", required = true, type = "String")
    private String password;

    @NotBlank(message = "验证码不能为空")
    @Schema(description = "code", title = "邮箱验证码", required = true, type = "String")
    private String code;

}
