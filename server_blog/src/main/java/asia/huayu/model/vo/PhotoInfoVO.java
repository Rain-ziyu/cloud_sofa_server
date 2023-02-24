package asia.huayu.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "照片信息")
public class PhotoInfoVO {

    @NotNull(message = "照片id不能为空")
    @Schema(name = "id", title = "照片id", required = true, type = "Integer")
    private Integer id;

    @NotBlank(message = "照片名不能为空")
    @Schema(name = "photoName", title = "照片名", required = true, type = "String")
    private String photoName;

    @Schema(name = "photoDesc", title = "照片描述", type = "String")
    private String photoDesc;

}
