package com.huayu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huayu.entity.PhotoAlbum;
import com.huayu.model.dto.PageResultDTO;
import com.huayu.model.dto.PhotoAlbumAdminDTO;
import com.huayu.model.dto.PhotoAlbumDTO;
import com.huayu.model.vo.ConditionVO;
import com.huayu.model.vo.PhotoAlbumVO;

import java.util.List;

public interface PhotoAlbumService extends IService<PhotoAlbum> {

    void saveOrUpdatePhotoAlbum(PhotoAlbumVO photoAlbumVO);

    PageResultDTO<PhotoAlbumAdminDTO> listPhotoAlbumsAdmin(ConditionVO condition);

    List<PhotoAlbumDTO> listPhotoAlbumInfosAdmin();

    PhotoAlbumAdminDTO getPhotoAlbumByIdAdmin(Integer albumId);

    void deletePhotoAlbumById(Integer albumId);

    List<PhotoAlbumDTO> listPhotoAlbums();

}
