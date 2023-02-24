package asia.huayu.controller;


import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.TagDTO;
import asia.huayu.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "标签模块")
@RestController
public class TagController {


    @Autowired
    private TagService tagService;

    @Operation(summary = "获取所有标签")
    @GetMapping("/tags/all")
    public Result<List<TagDTO>> getAllTags() {
        return Result.OK(tagService.listTags());
    }

    @Operation(summary = "获取前十个标签")
    @GetMapping("/tags/topTen")
    public Result<List<TagDTO>> getTopTenTags() {
        return Result.OK(tagService.listTopTenTags());
    }
}
