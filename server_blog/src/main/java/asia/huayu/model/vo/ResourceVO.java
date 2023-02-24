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

    @Schema(name = "id", title = "资源id", required = true, type = "Integer")
    private Integer id;

    @NotBlank(message = "资源名不能为空")
    @Schema(name = "resourceName", title = "资源名", required = true, type = "String")
    private String resourceName;

    @Schema(name = "url", title = "资源路径", required = true, type = "String")
    private String url;

    @Schema(name = "url", title = "资源路径", required = true, type = "String")
    private String requestMethod;

    @Schema(name = "parentId", title = "父资源id", required = true, type = "Integer")
    private Integer parentId;

    @Schema(name = "isAnonymous", title = "是否匿名访问", required = true, type = "Integer")
    private Integer isAnonymous;

}
