package asia.huayu.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "密码")
public class PasswordVO {

    @NotBlank(message = "旧密码不能为空")
    @Schema(description = "oldPassword", title = "旧密码", required = true, type = "String")
    private String oldPassword;

    @Size(min = 6, message = "新密码不能少于6位")
    @NotBlank(message = "新密码不能为空")
    @Schema(description = "newPassword", title = "新密码", required = true, type = "String")
    private String newPassword;
}
