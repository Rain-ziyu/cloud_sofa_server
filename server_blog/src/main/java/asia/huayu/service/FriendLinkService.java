package asia.huayu.service;

import asia.huayu.entity.FriendLink;
import asia.huayu.model.dto.FriendLinkDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface FriendLinkService extends IService<FriendLink> {

    List<FriendLinkDTO> listFriendLinks();


}
