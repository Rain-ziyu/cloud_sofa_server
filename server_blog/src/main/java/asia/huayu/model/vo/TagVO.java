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
@Schema(description = "标签对象")
public class TagVO {

    @Schema(name = "id", title = "标签id", type = "Integer")
    private Integer id;

    @NotBlank(message = "标签名不能为空")
    @Schema(name = "categoryName", title = "标签名", required = true, type = "String")
    private String tagName;

}
