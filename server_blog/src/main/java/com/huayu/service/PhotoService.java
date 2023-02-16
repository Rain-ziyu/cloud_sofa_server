package com.huayu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huayu.entity.Photo;
import com.huayu.model.dto.PageResultDTO;
import com.huayu.model.dto.PhotoAdminDTO;
import com.huayu.model.dto.PhotoDTO;
import com.huayu.model.vo.ConditionVO;
import com.huayu.model.vo.DeleteVO;
import com.huayu.model.vo.PhotoInfoVO;
import com.huayu.model.vo.PhotoVO;

import java.util.List;

public interface PhotoService extends IService<Photo> {

    PageResultDTO<PhotoAdminDTO> listPhotos(ConditionVO conditionVO);

    void updatePhoto(PhotoInfoVO photoInfoVO);

    void savePhotos(PhotoVO photoVO);

    void updatePhotosAlbum(PhotoVO photoVO);

    void updatePhotoDelete(DeleteVO deleteVO);

    void deletePhotos(List<Integer> photoIds);

    PhotoDTO listPhotosByAlbumId(Integer albumId);

}
