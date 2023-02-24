package asia.huayu.controller;

import asia.huayu.annotation.OptLog;
import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.JobLogDTO;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.vo.JobLogSearchVO;
import asia.huayu.service.JobLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static asia.huayu.constant.OptTypeConstant.DELETE;

@Tag(name = "定时任务日志模块")
@RestController
public class JobLogController {

    @Autowired
    private JobLogService jobLogService;

    @Operation(summary = "获取定时任务的日志列表")
    @GetMapping("/jobLogs")
    public Result<PageResultDTO<JobLogDTO>> listJobLogs(JobLogSearchVO jobLogSearchVO) {
        return Result.OK(jobLogService.listJobLogs(jobLogSearchVO));
    }

    @OptLog(optType = DELETE)
    @Operation(summary = "删除定时任务的日志")
    @DeleteMapping("/jobLogs")
    public Result<?> deleteJobLogs(@RequestBody List<Integer> ids) {
        jobLogService.deleteJobLogs(ids);
        return Result.OK();
    }

    @OptLog(optType = DELETE)
    @Operation(summary = "清除定时任务的日志")
    @DeleteMapping("/jobLogs/clean")
    public Result<?> cleanJobLogs() {
        jobLogService.cleanJobLogs();
        return Result.OK();
    }

    @Operation(summary = "获取定时任务日志的所有组名")
    @GetMapping("/jobLogs/jobGroups")
    public Result<?> listJobLogGroups() {
        return Result.OK(jobLogService.listJobLogGroups());
    }
}
