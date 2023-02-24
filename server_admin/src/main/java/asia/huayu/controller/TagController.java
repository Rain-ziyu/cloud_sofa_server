package asia.huayu.controller;


import asia.huayu.annotation.OptLog;
import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.dto.TagAdminDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.TagVO;
import asia.huayu.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static asia.huayu.constant.OptTypeConstant.DELETE;
import static asia.huayu.constant.OptTypeConstant.SAVE_OR_UPDATE;

@Tag(name = "标签模块")
@RestController
public class TagController {


    @Autowired
    private TagService tagService;


    @Operation(summary = "查询后台标签列表")
    @GetMapping("/tags")
    public Result<PageResultDTO<TagAdminDTO>> listTagsAdmin(ConditionVO conditionVO) {
        return Result.OK(tagService.listTagsAdmin(conditionVO));
    }

    @Operation(summary = "搜索文章标签")
    @GetMapping("/tags/search")
    public Result<List<TagAdminDTO>> listTagsAdminBySearch(ConditionVO condition) {
        return Result.OK(tagService.listTagsAdminBySearch(condition));
    }

    @OptLog(optType = SAVE_OR_UPDATE)
    @Operation(summary = "添加或修改标签")
    @PostMapping("/tags")
    public Result<?> saveOrUpdateTag(@Valid @RequestBody TagVO tagVO) {
        tagService.saveOrUpdateTag(tagVO);
        return Result.OK();
    }

    @OptLog(optType = DELETE)
    @Operation(summary = "删除标签")
    @DeleteMapping("/tags")
    public Result<?> deleteTag(@RequestBody List<Integer> tagIdList) {
        tagService.deleteTag(tagIdList);
        return Result.OK();
    }
}
