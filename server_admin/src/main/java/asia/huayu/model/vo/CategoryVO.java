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
@Schema(description = "分类")
public class CategoryVO {

    @Schema(description = "id", title = "分类id", type = "Integer")
    private Integer id;

    @NotBlank(message = "分类名不能为空")
    @Schema(description = "categoryName", title = "分类名", required = true, type = "String")
    private String categoryName;

}
