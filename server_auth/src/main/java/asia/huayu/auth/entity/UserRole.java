package asia.huayu.auth.entity;

import com.baomidou.mybatisplus.annotation.*;

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

public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.AUTO)
    private String id;


    private String roleId;


    private String userId;


    private Boolean isDeleted;


    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
