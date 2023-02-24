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
@Schema(description = "菜单")
public class MenuVO {

    @Schema(name = "id", title = "菜单id", type = "Integer")
    private Integer id;

    @NotBlank(message = "菜单名不能为空")
    @Schema(name = "name", title = "菜单名", type = "String")
    private String name;

    @NotBlank(message = "菜单icon不能为空")
    @Schema(name = "icon", title = "菜单icon", type = "String")
    private String icon;

    @NotBlank(message = "路径不能为空")
    @Schema(name = "path", title = "路径", type = "String")
    private String path;

    @NotBlank(message = "组件不能为空")
    @Schema(name = "component", title = "组件", type = "String")
    private String component;

    @NotNull(message = "排序不能为空")
    @Schema(name = "orderNum", title = "排序", type = "Integer")
    private Integer orderNum;

    @Schema(name = "parentId", title = "父id", type = "Integer")
    private Integer parentId;
    @Schema(name = "type", title = "菜单类型", type = "Integer")
    private Integer type;
    @Schema(name = "permissionValue", title = "权限值 菜单类型为按钮时需要有对应权限", type = "String")
    private String permissionValue;
    @Schema(name = "isHidden", title = "是否隐藏", type = "Integer")
    private Integer isHidden;

}
