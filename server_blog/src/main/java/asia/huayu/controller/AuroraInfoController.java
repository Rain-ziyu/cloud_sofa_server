package asia.huayu.controller;

import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.AboutDTO;
import asia.huayu.model.dto.AuroraHomeInfoDTO;
import asia.huayu.service.AuroraInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "aurora信息")
@RestController
public class AuroraInfoController {

    @Autowired
    private AuroraInfoService auroraInfoService;


    @ApiOperation(value = "上报访客信息")
    @PostMapping("/report")
    public Result<?> report() {
        auroraInfoService.report();
        return Result.OK();
    }

    @ApiOperation(value = "获取系统信息")
    @GetMapping("/")
    public Result<AuroraHomeInfoDTO> getBlogHomeInfo() {
        return Result.OK(auroraInfoService.getAuroraHomeInfo());
    }
    @ApiOperation(value = "查看关于我信息")
    @GetMapping("/about")
    public Result<AboutDTO> getAbout() {
        return Result.OK(auroraInfoService.getAbout());
    }

}
