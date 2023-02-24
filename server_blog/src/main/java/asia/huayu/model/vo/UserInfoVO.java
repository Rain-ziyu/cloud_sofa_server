package asia.huayu.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户信息对象")
public class UserInfoVO {

    @NotBlank(message = "昵称不能为空")
    @Schema(name = "nickname", title = "昵称", type = "String")
    private String nickname;

    @Schema(name = "intro", title = "介绍", type = "String")
    private String intro;

    @Schema(name = "website", title = "个人网站", type = "String")
    private String website;

}
