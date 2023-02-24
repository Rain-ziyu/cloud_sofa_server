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
public class JobLogSearchVO {

    @Schema(description = "任务Id", title = "jobId", type = "Integer")
    private Integer jobId;

    @Schema(description = "任务名称", title = "jobName", type = "String")
    private String jobName;

    @Schema(description = "任务的组别", title = "jobGroup", type = "String")
    private String jobGroup;

    @Schema(description = "任务状态", title = "status", type = "Integer")
    private Integer status;

    @Schema(description = "开始时间", title = "startTime", type = "String")
    private String startTime;

    @Schema(description = "结束时间", title = "endTime", type = "String")
    private String endTime;
}
