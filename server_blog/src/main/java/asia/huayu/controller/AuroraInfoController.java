package asia.huayu.controller;

import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.AboutDTO;
import asia.huayu.model.dto.AuroraHomeInfoDTO;
import asia.huayu.service.AuroraInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "aurora信息")
@RestController
public class AuroraInfoController {

    @Autowired
    private AuroraInfoService auroraInfoService;


    @Operation(summary = "上报访客信息")
    @PostMapping("/report")
    public Result<?> report() {
        auroraInfoService.report();
        return Result.OK();
    }

    @Operation(summary = "获取系统信息")
    @GetMapping("/")
    public Result<AuroraHomeInfoDTO> getBlogHomeInfo() {
        return Result.OK(auroraInfoService.getAuroraHomeInfo());
    }

    @Operation(summary = "查看关于我信息")
    @GetMapping("/about")
    public Result<AboutDTO> getAbout() {
        return Result.OK(auroraInfoService.getAbout());
    }

}
