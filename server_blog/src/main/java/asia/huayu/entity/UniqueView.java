package asia.huayu.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author 花未眠
 * 网站访问量
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("unique_view")
public class UniqueView {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer viewsCount;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
