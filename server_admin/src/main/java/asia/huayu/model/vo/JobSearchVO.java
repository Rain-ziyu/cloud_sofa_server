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
public class JobSearchVO {

    @Schema(description = "任务名称", title = "jobName", required = true, type = "String")
    private String jobName;

    @Schema(description = "任务组别", title = "jobGroup", required = true, type = "String")
    private String jobGroup;

    @Schema(description = "任务状态", title = "status", required = true, type = "Integer")
    private Integer status;
}
