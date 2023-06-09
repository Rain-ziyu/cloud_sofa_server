package asia.huayu.controller;

import asia.huayu.annotation.OptLog;
import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.OperationLogDTO;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.service.OperationLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static asia.huayu.constant.OptTypeConstant.DELETE;

@Tag(name = "操作日志模块")
@RestController
public class OperationLogController {

    @Autowired
    private OperationLogService operationLogService;

    @Operation(summary = "查看操作日志")
    @GetMapping("/operation/logs")
    public Result<PageResultDTO<OperationLogDTO>> listOperationLogs(ConditionVO conditionVO) {
        return Result.OK(operationLogService.listOperationLogs(conditionVO));
    }

    @OptLog(optType = DELETE)
    @Operation(summary = "删除操作日志")
    @DeleteMapping("/operation/logs")
    public Result<?> deleteOperationLogs(@RequestBody List<Integer> operationLogIds) {
        operationLogService.removeByIds(operationLogIds);
        return Result.OK();
    }

}
