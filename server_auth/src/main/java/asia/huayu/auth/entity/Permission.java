package asia.huayu.auth.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 权限
 *
 * @author testjava
 * @since 2020-01-12
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("permission")
@Schema(name = "Permission对象", description = "权限")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "所属上级")
    private Integer parentId;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "类型(1:菜单,2:按钮)")
    private Integer type;

    @Schema(description = "权限值")
    private String permissionValue;

    @Schema(description = "菜单路径")
    private String path;

    @Schema(description = "组件路径")
    private String component;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "状态(0:禁止,1:正常)")
    private Integer status;
    @Schema(description = "排序参数")
    // 注意这里的rank是mysql的关键字需要``进行包裹
    @TableField("`rank`")
    private Integer rank;


    @Schema(description = "下级")
    @TableField(exist = false)
    private List<Permission> children;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
