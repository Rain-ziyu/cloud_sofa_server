package com.huayu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huayu.entity.FriendLink;
import com.huayu.model.dto.FriendLinkAdminDTO;
import com.huayu.model.dto.FriendLinkDTO;
import com.huayu.model.dto.PageResultDTO;
import com.huayu.model.vo.ConditionVO;
import com.huayu.model.vo.FriendLinkVO;

import java.util.List;

public interface FriendLinkService extends IService<FriendLink> {

    List<FriendLinkDTO> listFriendLinks();

    PageResultDTO<FriendLinkAdminDTO> listFriendLinksAdmin(ConditionVO conditionVO);

    void saveOrUpdateFriendLink(FriendLinkVO friendLinkVO);

}
