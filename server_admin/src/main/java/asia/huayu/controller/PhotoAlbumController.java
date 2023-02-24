package asia.huayu.controller;

import asia.huayu.annotation.OptLog;
import asia.huayu.common.entity.Result;
import asia.huayu.enums.FilePathEnum;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.dto.PhotoAlbumAdminDTO;
import asia.huayu.model.dto.PhotoAlbumDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.PhotoAlbumVO;
import asia.huayu.service.PhotoAlbumService;
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

@Tag(name = "相册模块")
@RestController
public class PhotoAlbumController {

    @Autowired
    private UploadStrategyContext uploadStrategyContext;

    @Autowired
    private PhotoAlbumService photoAlbumService;


    @OptLog(optType = UPLOAD)
    @Operation(summary = "上传相册封面")
    @Parameter(name = "file", description = "相册封面", required = true)
    @PostMapping("/photos/albums/upload")
    public Result<String> savePhotoAlbumCover(MultipartFile file) {
        return Result.OK(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.PHOTO.getPath()));
    }

    @OptLog(optType = SAVE_OR_UPDATE)
    @Operation(summary = "保存或更新相册")
    @PostMapping("/photos/albums")
    public Result<?> saveOrUpdatePhotoAlbum(@Valid @RequestBody PhotoAlbumVO photoAlbumVO) {
        photoAlbumService.saveOrUpdatePhotoAlbum(photoAlbumVO);
        return Result.OK();
    }

    @Operation(summary = "查看后台相册列表")
    @GetMapping("/photos/albums")
    public Result<PageResultDTO<PhotoAlbumAdminDTO>> listPhotoAlbumBacks(ConditionVO conditionVO) {
        return Result.OK(photoAlbumService.listPhotoAlbumsAdmin(conditionVO));
    }

    @Operation(summary = "获取后台相册列表信息")
    @GetMapping("/photos/albums/info")
    public Result<List<PhotoAlbumDTO>> listPhotoAlbumBackInfos() {
        return Result.OK(photoAlbumService.listPhotoAlbumInfosAdmin());
    }

    @Operation(summary = "根据id获取后台相册信息")
    @Parameter(name = "albumId", description = "相册id", required = true)
    @GetMapping("/photos/albums/{albumId}/info")
    public Result<PhotoAlbumAdminDTO> getPhotoAlbumBackById(@PathVariable("albumId") Integer albumId) {
        return Result.OK(photoAlbumService.getPhotoAlbumByIdAdmin(albumId));
    }

    @OptLog(optType = DELETE)
    @Operation(summary = "根据id删除相册")
    @Parameter(name = "albumId", description = "相册id", required = true)
    @DeleteMapping("/photos/albums/{albumId}")
    public Result<?> deletePhotoAlbumById(@PathVariable("albumId") Integer albumId) {
        photoAlbumService.deletePhotoAlbumById(albumId);
        return Result.OK();
    }

}
