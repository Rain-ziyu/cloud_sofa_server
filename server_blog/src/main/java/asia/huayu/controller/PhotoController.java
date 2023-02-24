package asia.huayu.controller;

import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.PhotoDTO;
import asia.huayu.service.PhotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "照片模块")
@RestController
public class PhotoController {

    @Autowired
    private PhotoService photoService;


    @Operation(summary = "根据相册id查看照片列表")
    @GetMapping("/albums/{albumId}/photos")
    public Result<PhotoDTO> listPhotosByAlbumId(@PathVariable("albumId") Integer albumId) {
        return Result.OK(photoService.listPhotosByAlbumId(albumId));
    }

}
