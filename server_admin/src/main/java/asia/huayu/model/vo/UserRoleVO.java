package asia.huayu.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户权限")
public class UserRoleVO {

    @NotNull(message = "id不能为空")
    @Schema(description = "userId", title = "用户id", type = "Integer")
    private Integer id;


    @NotNull(message = "用户角色不能为空")
    @Schema(description = "roleList", title = "角色id集合", type = "List<Integer>")
    private List<Integer> roleIds;

}
