package asia.huayu.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobStatusVO {

    @Schema(name = "任务id", title = "id", required = true, type = "Integer")
    private Integer id;

    @Schema(name = "任务状态", title = "status", required = true, type = "Integer")
    private Integer status;
}
