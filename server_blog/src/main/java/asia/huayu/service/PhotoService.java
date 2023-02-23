package asia.huayu.service;

import asia.huayu.entity.Photo;
import asia.huayu.model.dto.PhotoDTO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface PhotoService extends IService<Photo> {


    PhotoDTO listPhotosByAlbumId(Integer albumId);

}
