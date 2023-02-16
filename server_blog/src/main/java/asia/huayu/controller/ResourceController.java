package asia.huayu.controller;

import asia.huayu.annotation.OptLog;
import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.LabelOptionDTO;
import asia.huayu.model.dto.ResourceDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.service.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static asia.huayu.constant.OptTypeConstant.DELETE;

@Api(tags = "资源模块")
@RestController
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @ApiOperation(value = "查看资源列表")
    @GetMapping("/admin/resources")
    public Result<List<ResourceDTO>> listResources(ConditionVO conditionVO) {
        return Result.OK(resourceService.listResources(conditionVO));
    }

    @OptLog(optType = DELETE)
    @ApiOperation(value = "删除资源")
    @DeleteMapping("/admin/resources/{resourceId}")
    public Result<?> deleteResource(@PathVariable("resourceId") Integer resourceId) {
        resourceService.deleteResource(resourceId);
        return Result.OK();
    }

/*     @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "新增或修改资源")
    @PostMapping("/admin/resources")
    public Result<?> saveOrUpdateResource(@RequestBody @Valid ResourceVO resourceVO) {
        resourceService.saveOrUpdateResource(resourceVO);
        return Result.ok();
    } */

    @ApiOperation(value = "查看角色资源选项")
    @GetMapping("/admin/role/resources")
    public Result<List<LabelOptionDTO>> listResourceOption() {
        return Result.OK(resourceService.listResourceOption());
    }
}
