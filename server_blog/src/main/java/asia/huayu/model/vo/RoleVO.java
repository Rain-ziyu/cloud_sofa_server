package asia.huayu.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "角色")
public class RoleVO {

    @Schema(name = "id", title = "角色id", type = "Integer")
    private Integer id;

    @NotBlank(message = "角色名不能为空")
    @Schema(name = "roleName", title = "角色名", required = true, type = "String")
    private String roleName;

    @Schema(name = "resourceIdList", title = "资源列表", required = true, type = "List<Integer>")
    private List<Integer> resourceIds;

    @Schema(name = "menuIdList", title = "菜单列表", required = true, type = "List<Integer>")
    private List<Integer> menuIds;

}
