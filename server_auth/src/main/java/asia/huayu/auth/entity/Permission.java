package asia.huayu.auth.entity;

import com.baomidou.mybatisplus.annotation.*;
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

public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    private Integer parentId;


    private String name;


    private Integer type;


    private String permissionValue;


    private String path;


    private String component;


    private String icon;


    private Integer status;

    // 注意这里的rank是mysql的关键字需要``进行包裹
    @TableField("`rank`")
    private Integer rank;


    @TableField(exist = false)
    private List<Permission> children;


    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
