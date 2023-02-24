package asia.huayu.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "审核")
public class ReviewVO {

    @NotNull(message = "id不能为空")
    @Schema(name = "idList", title = "id列表", required = true, type = "List<Integer>")
    private List<Integer> ids;

    @NotNull(message = "状态值不能为空")
    @Schema(name = "isDelete", title = "删除状态", required = true, type = "Integer")
    private Integer isReview;

}
