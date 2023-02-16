package asia.huayu.service;

import asia.huayu.entity.PhotoAlbum;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.dto.PhotoAlbumAdminDTO;
import asia.huayu.model.dto.PhotoAlbumDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.PhotoAlbumVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface PhotoAlbumService extends IService<PhotoAlbum> {

    void saveOrUpdatePhotoAlbum(PhotoAlbumVO photoAlbumVO);

    PageResultDTO<PhotoAlbumAdminDTO> listPhotoAlbumsAdmin(ConditionVO condition);

    List<PhotoAlbumDTO> listPhotoAlbumInfosAdmin();

    PhotoAlbumAdminDTO getPhotoAlbumByIdAdmin(Integer albumId);

    void deletePhotoAlbumById(Integer albumId);

    List<PhotoAlbumDTO> listPhotoAlbums();

}
