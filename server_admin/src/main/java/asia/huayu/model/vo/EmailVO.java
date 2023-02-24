package asia.huayu.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "绑定邮箱")
public class EmailVO {

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @Schema(description = "email", title = "用户名", required = true, type = "String")
    private String email;

    @NotBlank(message = "验证码不能为空")
    @Schema(description = "code", title = "邮箱验证码", required = true, type = "String")
    private String code;

}
