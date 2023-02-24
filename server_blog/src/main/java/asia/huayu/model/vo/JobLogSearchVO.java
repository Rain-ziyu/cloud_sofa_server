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

    @Schema(name = "任务Id", title = "jobId", type = "Integer")
    private Integer jobId;

    @Schema(name = "任务名称", title = "jobName", type = "String")
    private String jobName;

    @Schema(name = "任务的组别", title = "jobGroup", type = "String")
    private String jobGroup;

    @Schema(name = "任务状态", title = "status", type = "Integer")
    private Integer status;

    @Schema(name = "开始时间", title = "startTime", type = "String")
    private String startTime;

    @Schema(name = "结束时间", title = "endTime", type = "String")
    private String endTime;
}
