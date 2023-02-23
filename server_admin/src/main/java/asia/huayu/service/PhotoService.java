package asia.huayu.service;

import asia.huayu.entity.Photo;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.dto.PhotoAdminDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.DeleteVO;
import asia.huayu.model.vo.PhotoInfoVO;
import asia.huayu.model.vo.PhotoVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface PhotoService extends IService<Photo> {

    PageResultDTO<PhotoAdminDTO> listPhotos(ConditionVO conditionVO);

    void updatePhoto(PhotoInfoVO photoInfoVO);

    void savePhotos(PhotoVO photoVO);

    void updatePhotosAlbum(PhotoVO photoVO);

    void updatePhotoDelete(DeleteVO deleteVO);

    void deletePhotos(List<Integer> photoIds);


}
