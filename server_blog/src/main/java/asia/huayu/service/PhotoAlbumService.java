package asia.huayu.service;

import asia.huayu.entity.PhotoAlbum;
import asia.huayu.model.dto.PhotoAlbumDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface PhotoAlbumService extends IService<PhotoAlbum> {


    List<PhotoAlbumDTO> listPhotoAlbums();

}
