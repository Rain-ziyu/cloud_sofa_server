package asia.huayu.controller;

import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.PhotoAlbumDTO;
import asia.huayu.service.PhotoAlbumService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "相册模块")
@RestController
public class PhotoAlbumController {


    @Autowired
    private PhotoAlbumService photoAlbumService;



    @ApiOperation(value = "获取相册列表")
    @GetMapping("/photos/albums")
    public Result<List<PhotoAlbumDTO>> listPhotoAlbums() {
        return Result.OK(photoAlbumService.listPhotoAlbums());
    }

}
