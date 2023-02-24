package asia.huayu.controller;

import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.PhotoAlbumDTO;
import asia.huayu.service.PhotoAlbumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "相册模块")
@RestController
public class PhotoAlbumController {


    @Autowired
    private PhotoAlbumService photoAlbumService;


    @Operation(summary = "获取相册列表")
    @GetMapping("/photos/albums")
    public Result<List<PhotoAlbumDTO>> listPhotoAlbums() {
        return Result.OK(photoAlbumService.listPhotoAlbums());
    }

}
