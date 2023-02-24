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
@Schema(description = "资源")
public class ResourceVO {

    @Schema(description = "id", title = "资源id", required = true, type = "Integer")
    private Integer id;

    @NotBlank(message = "资源名不能为空")
    @Schema(description = "resourceName", title = "资源名", required = true, type = "String")
    private String resourceName;

    @Schema(description = "url", title = "资源路径", required = true, type = "String")
    private String url;

    @Schema(description = "url", title = "资源路径", required = true, type = "String")
    private String requestMethod;

    @Schema(description = "parentId", title = "父资源id", required = true, type = "Integer")
    private Integer parentId;

    @Schema(description = "isAnonymous", title = "是否匿名访问", required = true, type = "Integer")
    private Integer isAnonymous;

}
