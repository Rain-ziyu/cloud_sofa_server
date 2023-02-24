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

    @Schema(description = "id", title = "菜单id", type = "Integer")
    private Integer id;

    @NotBlank(message = "菜单名不能为空")
    @Schema(description = "name", title = "菜单名", type = "String")
    private String name;

    @NotBlank(message = "菜单icon不能为空")
    @Schema(description = "icon", title = "菜单icon", type = "String")
    private String icon;

    @NotBlank(message = "路径不能为空")
    @Schema(description = "path", title = "路径", type = "String")
    private String path;

    @NotBlank(message = "组件不能为空")
    @Schema(description = "component", title = "组件", type = "String")
    private String component;

    @NotNull(message = "排序不能为空")
    @Schema(description = "orderNum", title = "排序", type = "Integer")
    private Integer orderNum;

    @Schema(description = "parentId", title = "父id", type = "Integer")
    private Integer parentId;
    @Schema(description = "type", title = "菜单类型", type = "Integer")
    private Integer type;
    @Schema(description = "permissiontitle = ", title = "权限值 菜单类型为按钮时需要有对应权限", type = "String")
    private String permissionValue;
    @Schema(description = "isHidden", title = "是否隐藏", type = "Integer")
    private Integer isHidden;

}
