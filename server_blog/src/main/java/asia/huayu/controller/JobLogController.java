package asia.huayu.controller;

import asia.huayu.annotation.OptLog;
import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.JobLogDTO;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.vo.JobLogSearchVO;
import asia.huayu.service.JobLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static asia.huayu.constant.OptTypeConstant.DELETE;

@Api(tags = "定时任务日志模块")
@RestController
public class JobLogController {

    @Autowired
    private JobLogService jobLogService;

    @ApiOperation("获取定时任务的日志列表")
    @GetMapping("/admin/jobLogs")
    public Result<PageResultDTO<JobLogDTO>> listJobLogs(JobLogSearchVO jobLogSearchVO) {
        return Result.OK(jobLogService.listJobLogs(jobLogSearchVO));
    }

    @OptLog(optType = DELETE)
    @ApiOperation("删除定时任务的日志")
    @DeleteMapping("/admin/jobLogs")
    public Result<?> deleteJobLogs(@RequestBody List<Integer> ids) {
        jobLogService.deleteJobLogs(ids);
        return Result.OK();
    }

    @OptLog(optType = DELETE)
    @ApiOperation("清除定时任务的日志")
    @DeleteMapping("/admin/jobLogs/clean")
    public Result<?> cleanJobLogs() {
        jobLogService.cleanJobLogs();
        return Result.OK();
    }

    @ApiOperation("获取定时任务日志的所有组名")
    @GetMapping("/admin/jobLogs/jobGroups")
    public Result<?> listJobLogGroups() {
        return Result.OK(jobLogService.listJobLogGroups());
    }
}
