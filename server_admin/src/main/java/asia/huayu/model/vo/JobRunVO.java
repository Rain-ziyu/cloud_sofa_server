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
public class JobRunVO {

    @Schema(description = "任务id", title = "id", required = true, type = "Integer")
    private Integer id;

    @Schema(description = "任务组别", title = "jobGroup", required = true, type = "String")
    private String jobGroup;
}
