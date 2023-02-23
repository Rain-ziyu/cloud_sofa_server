package asia.huayu.service;

import asia.huayu.entity.FriendLink;
import asia.huayu.model.dto.FriendLinkAdminDTO;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.FriendLinkVO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface FriendLinkService extends IService<FriendLink> {

    PageResultDTO<FriendLinkAdminDTO> listFriendLinksAdmin(ConditionVO conditionVO);

    void saveOrUpdateFriendLink(FriendLinkVO friendLinkVO);

}
