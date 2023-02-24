package asia.huayu.service;

import asia.huayu.entity.UserInfo;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.dto.UserInfoDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.UserDisableVO;
import asia.huayu.model.vo.UserRoleVO;
import asia.huayu.security.entity.OnlineUser;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserInfoService extends IService<UserInfo> {


    void updateUserRole(UserRoleVO userRoleVO);

    void updateUserDisable(UserDisableVO userDisableVO);

    PageResultDTO<OnlineUser> listOnlineUsers(ConditionVO conditionVO);

    void removeOnlineUser(Integer userInfoId);


    UserInfo getUserInfoByName(String username);


    UserInfoDTO getUserInfo();
}
