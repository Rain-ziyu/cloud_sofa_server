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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static asia.huayu.constant.OptTypeConstant.UPDATE;
import static asia.huayu.constant.OptTypeConstant.UPLOAD;

@Api(tags = "aurora信息")
@RestController
public class AuroraInfoController {

    @Autowired
    private AuroraInfoService auroraInfoService;

    @Autowired
    private UploadStrategyContext uploadStrategyContext;

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

    @ApiOperation(value = "获取系统后台信息")
    @GetMapping("/admin")
    public Result<AuroraAdminInfoDTO> getBlogBackInfo() {
        return Result.OK(auroraInfoService.getAuroraAdminInfo());
    }

    @OptLog(optType = UPDATE)
    @ApiOperation(value = "更新网站配置")
    @PutMapping("/admin/website/config")
    public Result<?> updateWebsiteConfig(@Valid @RequestBody WebsiteConfigVO websiteConfigVO) {
        auroraInfoService.updateWebsiteConfig(websiteConfigVO);
        return Result.OK();
    }

    @ApiOperation(value = "获取网站配置")
    @GetMapping("/admin/website/config")
    public Result<WebsiteConfigDTO> getWebsiteConfig() {
        return Result.OK(auroraInfoService.getWebsiteConfig());
    }

    @ApiOperation(value = "查看关于我信息")
    @GetMapping("/about")
    public Result<AboutDTO> getAbout() {
        return Result.OK(auroraInfoService.getAbout());
    }

    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改关于我信息")
    @PutMapping("/admin/about")
    public Result<?> updateAbout(@Valid @RequestBody AboutVO aboutVO) {
        auroraInfoService.updateAbout(aboutVO);
        return Result.OK();
    }

    @OptLog(optType = UPLOAD)
    @ApiOperation(value = "上传博客配置图片")
    @ApiImplicitParam(name = "file", value = "图片", required = true, dataType = "MultipartFile")
    @PostMapping("/admin/config/images")
    public Result<String> savePhotoAlbumCover(MultipartFile file) {
        return Result.OK(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.CONFIG.getPath()));
    }

}
