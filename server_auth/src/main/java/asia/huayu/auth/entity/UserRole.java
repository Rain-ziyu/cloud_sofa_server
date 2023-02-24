package asia.huayu.auth.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_role")
@Schema(name = "UserRole对象", description = "")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @Schema(description = "角色id")
    private String roleId;

    @Schema(description = "用户id")
    private String userId;

    @Schema(description = "逻辑删除 1（true）已删除， 0（false）未删除")
    private Boolean isDeleted;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
