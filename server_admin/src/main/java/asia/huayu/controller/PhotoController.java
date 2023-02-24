package asia.huayu.controller;

import asia.huayu.annotation.OptLog;
import asia.huayu.common.entity.Result;
import asia.huayu.enums.FilePathEnum;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.dto.PhotoAdminDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.DeleteVO;
import asia.huayu.model.vo.PhotoInfoVO;
import asia.huayu.model.vo.PhotoVO;
import asia.huayu.service.PhotoService;
import asia.huayu.strategy.context.UploadStrategyContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static asia.huayu.constant.OptTypeConstant.*;

@Tag(name = "照片模块")
@RestController
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private UploadStrategyContext uploadStrategyContext;

    @OptLog(optType = UPLOAD)
    @Operation(summary = "上传照片")
    @Parameter(name = "file", description = "照片", required = true)
    @PostMapping("/photos/upload")
    public Result<String> savePhotoAlbumCover(MultipartFile file) {
        return Result.OK(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.PHOTO.getPath()));
    }

    @Operation(summary = "根据相册id获取照片列表")
    @GetMapping("/photos")
    public Result<PageResultDTO<PhotoAdminDTO>> listPhotos(ConditionVO conditionVO) {
        return Result.OK(photoService.listPhotos(conditionVO));
    }

    @OptLog(optType = UPDATE)
    @Operation(summary = "更新照片信息")
    @PutMapping("/photos")
    public Result<?> updatePhoto(@Valid @RequestBody PhotoInfoVO photoInfoVO) {
        photoService.updatePhoto(photoInfoVO);
        return Result.OK();
    }

    @OptLog(optType = SAVE)
    @Operation(summary = "保存照片")
    @PostMapping("/photos")
    public Result<?> savePhotos(@Valid @RequestBody PhotoVO photoVO) {
        photoService.savePhotos(photoVO);
        return Result.OK();
    }

    @OptLog(optType = UPDATE)
    @Operation(summary = "移动照片相册")
    @PutMapping("/photos/album")
    public Result<?> updatePhotosAlbum(@Valid @RequestBody PhotoVO photoVO) {
        photoService.updatePhotosAlbum(photoVO);
        return Result.OK();
    }

    @OptLog(optType = UPDATE)
    @Operation(summary = "更新照片删除状态")
    @PutMapping("/photos/delete")
    public Result<?> updatePhotoDelete(@Valid @RequestBody DeleteVO deleteVO) {
        photoService.updatePhotoDelete(deleteVO);
        return Result.OK();
    }

    @OptLog(optType = DELETE)
    @Operation(summary = "删除照片")
    @DeleteMapping("/photos")
    public Result<?> deletePhotos(@RequestBody List<Integer> photoIds) {
        photoService.deletePhotos(photoIds);
        return Result.OK();
    }

}
