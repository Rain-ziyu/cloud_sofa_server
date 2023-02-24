package asia.huayu.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "qq登录信息")
public class QQLoginVO {

    @NotBlank(message = "openId不能为空")
    @Schema(name = "openId", title = "qq openId", required = true, type = "String")
    private String openId;

    @NotBlank(message = "accessToken不能为空")
    @Schema(name = "accessToken", title = "qq accessToken", required = true, type = "String")
    private String accessToken;

}
