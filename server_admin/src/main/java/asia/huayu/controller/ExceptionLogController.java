package asia.huayu.controller;

import asia.huayu.annotation.OptLog;
import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.ExceptionLogDTO;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.service.ExceptionLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static asia.huayu.constant.OptTypeConstant.DELETE;

@Tag(name = "异常日志模块")
@RestController
public class ExceptionLogController {

    @Autowired
    private ExceptionLogService exceptionLogService;

    @Operation(summary = "获取异常日志")
    @GetMapping("/exception/logs")
    public Result<PageResultDTO<ExceptionLogDTO>> listExceptionLogs(ConditionVO conditionVO) {
        return Result.OK(exceptionLogService.listExceptionLogs(conditionVO));
    }

    @OptLog(optType = DELETE)
    @Operation(summary = "删除异常日志")
    @DeleteMapping("/exception/logs")
    public Result<?> deleteExceptionLogs(@RequestBody List<Integer> exceptionLogIds) {
        exceptionLogService.removeByIds(exceptionLogIds);
        return Result.OK();
    }

}
