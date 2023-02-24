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
@Schema(description = "说说对象")
public class TalkVO {

    @Schema(description = "id", title = "说说id", type = "Integer")
    private Integer id;

    @Schema(description = "content", title = "说说内容", type = "String")
    @NotBlank(message = "说说内容不能为空")
    private String content;

    @Schema(description = "images", title = "说说图片", type = "String")
    private String images;

    @Schema(description = "isTop", title = "置顶状态", type = "Integer")
    @NotNull(message = "置顶状态不能为空")
    private Integer isTop;

    @Schema(description = "status", title = "说说状态", type = "Integer")
    @NotNull(message = "说说状态不能为空")
    private Integer status;

}
