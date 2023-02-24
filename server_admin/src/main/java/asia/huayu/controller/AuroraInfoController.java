package asia.huayu.controller;

import asia.huayu.annotation.OptLog;
import asia.huayu.common.entity.Result;
import asia.huayu.enums.FilePathEnum;
import asia.huayu.model.dto.AboutDTO;
import asia.huayu.model.dto.AuroraAdminInfoDTO;
import asia.huayu.model.dto.AuroraHomeInfoDTO;
import asia.huayu.model.dto.WebsiteConfigDTO;
import asia.huayu.model.vo.AboutVO;
import asia.huayu.model.vo.WebsiteConfigVO;
import asia.huayu.service.AuroraInfoService;
import asia.huayu.strategy.context.UploadStrategyContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static asia.huayu.constant.OptTypeConstant.UPDATE;
import static asia.huayu.constant.OptTypeConstant.UPLOAD;

@Tag(name = "aurora信息")
@RestController
public class AuroraInfoController {

    @Autowired
    private AuroraInfoService auroraInfoService;

    @Autowired
    private UploadStrategyContext uploadStrategyContext;

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

    @Operation(summary = "获取系统后台信息")
    @GetMapping("")
    public Result<AuroraAdminInfoDTO> getBlogBackInfo() {
        return Result.OK(auroraInfoService.getAuroraAdminInfo());
    }

    @OptLog(optType = UPDATE)
    @Operation(summary = "更新网站配置")
    @PutMapping("/website/config")
    public Result<?> updateWebsiteConfig(@Valid @RequestBody WebsiteConfigVO websiteConfigVO) {
        auroraInfoService.updateWebsiteConfig(websiteConfigVO);
        return Result.OK();
    }

    @Operation(summary = "获取网站配置")
    @GetMapping("/website/config")
    public Result<WebsiteConfigDTO> getWebsiteConfig() {
        return Result.OK(auroraInfoService.getWebsiteConfig());
    }

    @Operation(summary = "查看关于我信息")
    @GetMapping("/about")
    public Result<AboutDTO> getAbout() {
        return Result.OK(auroraInfoService.getAbout());
    }

    @OptLog(optType = UPDATE)
    @Operation(summary = "修改关于我信息")
    @PutMapping("/about")
    public Result<?> updateAbout(@Valid @RequestBody AboutVO aboutVO) {
        auroraInfoService.updateAbout(aboutVO);
        return Result.OK();
    }

    @OptLog(optType = UPLOAD)
    @Operation(summary = "上传博客配置图片")
    @Parameter(name = "file", description = "图片", required = true)
    @PostMapping("/config/images")
    public Result<String> savePhotoAlbumCover(MultipartFile file) {
        return Result.OK(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.CONFIG.getPath()));
    }

}
