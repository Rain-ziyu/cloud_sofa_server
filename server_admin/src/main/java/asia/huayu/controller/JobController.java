package asia.huayu.controller;

import asia.huayu.annotation.OptLog;
import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.JobDTO;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.vo.JobRunVO;
import asia.huayu.model.vo.JobSearchVO;
import asia.huayu.model.vo.JobStatusVO;
import asia.huayu.model.vo.JobVO;
import asia.huayu.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static asia.huayu.constant.OptTypeConstant.*;

@Tag(name = "定时任务模块")
@RestController
public class JobController {

    @Autowired
    private JobService jobService;

    @OptLog(optType = SAVE)
    @Operation(summary = "添加定时任务")
    @PostMapping("/jobs")
    public Result<?> saveJob(@RequestBody JobVO jobVO) {
        jobService.saveJob(jobVO);
        return Result.OK();
    }

    @OptLog(optType = UPDATE)
    @Operation(summary = "修改定时任务")
    @PutMapping("/jobs")
    public Result<?> updateJob(@RequestBody JobVO jobVO) {
        jobService.updateJob(jobVO);
        return Result.OK();
    }

    @OptLog(optType = DELETE)
    @Operation(summary = "删除定时任务")
    @DeleteMapping("/jobs")
    public Result<?> deleteJobById(@RequestBody List<Integer> jobIds) {
        jobService.deleteJobs(jobIds);
        return Result.OK();
    }

    @Operation(summary = "根据id获取任务")
    @GetMapping("/jobs/{id}")
    public Result<JobDTO> getJobById(@PathVariable("id") Integer jobId) {
        return Result.OK(jobService.getJobById(jobId));
    }

    @Operation(summary = "获取任务列表")
    @GetMapping("/jobs")
    public Result<PageResultDTO<JobDTO>> listJobs(JobSearchVO jobSearchVO) {
        return Result.OK(jobService.listJobs(jobSearchVO));
    }

    @Operation(summary = "更改任务的状态")
    @PutMapping("/jobs/status")
    public Result<?> updateJobStatus(@RequestBody JobStatusVO jobStatusVO) {
        jobService.updateJobStatus(jobStatusVO);
        return Result.OK();
    }

    @Operation(summary = "执行某个任务")
    @PutMapping("/jobs/run")
    public Result<?> runJob(@RequestBody JobRunVO jobRunVO) {
        jobService.runJob(jobRunVO);
        return Result.OK();
    }

    @Operation(summary = "获取所有job分组")
    @GetMapping("/jobs/jobGroups")
    public Result<List<String>> listJobGroup() {
        return Result.OK(jobService.listJobGroups());
    }
}
