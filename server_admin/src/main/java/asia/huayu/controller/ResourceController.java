package asia.huayu.controller;

import asia.huayu.annotation.OptLog;
import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.LabelOptionDTO;
import asia.huayu.model.dto.ResourceDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.ResourceVO;
import asia.huayu.service.ResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static asia.huayu.constant.OptTypeConstant.DELETE;
import static asia.huayu.constant.OptTypeConstant.SAVE_OR_UPDATE;

@Tag(name = "资源模块")
@RestController
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @Operation(summary = "查看资源列表")
    @GetMapping("/resources")
    public Result<List<ResourceDTO>> listResources(ConditionVO conditionVO) {
        return Result.OK(resourceService.listResources(conditionVO));
    }

    @OptLog(optType = DELETE)
    @Operation(summary = "删除资源")
    @DeleteMapping("/resources/{resourceId}")
    public Result<?> deleteResource(@PathVariable("resourceId") Integer resourceId) {
        resourceService.deleteResource(resourceId);
        return Result.OK();
    }

    @OptLog(optType = SAVE_OR_UPDATE)
    @Operation(summary = "新增或修改资源")
    @PostMapping("/resources")
    public Result<?> saveOrUpdateResource(@RequestBody @Valid ResourceVO resourceVO) {
        resourceService.saveOrUpdateResource(resourceVO);
        return Result.OK("更新资源成功");
    }

    @Operation(summary = "查看角色资源选项")
    @GetMapping("/role/resources")
    public Result<List<LabelOptionDTO>> listResourceOption() {
        return Result.OK(resourceService.listResourceOption());
    }
}
