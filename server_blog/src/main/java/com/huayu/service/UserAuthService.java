package com.huayu.service;

import com.huayu.model.dto.*;
import com.huayu.model.vo.ConditionVO;
import com.huayu.model.vo.PasswordVO;
import com.huayu.model.vo.QQLoginVO;
import com.huayu.model.vo.UserVO;

import java.util.List;

public interface UserAuthService {

    void sendCode(String username);

    List<UserAreaDTO> listUserAreas(ConditionVO conditionVO);

    void register(UserVO userVO);

    void updatePassword(UserVO userVO);

    void updateAdminPassword(PasswordVO passwordVO);

    PageResultDTO<UserAdminDTO> listUsers(ConditionVO condition);

    UserLogoutStatusDTO logout();

    UserInfoDTO qqLogin(QQLoginVO qqLoginVO);

}
