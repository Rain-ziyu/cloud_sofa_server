package asia.huayu.service;

import asia.huayu.entity.User;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.dto.UserAdminDTO;
import asia.huayu.model.dto.UserAreaDTO;
import asia.huayu.model.dto.UserDetailsDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.QQLoginVO;
import asia.huayu.model.vo.UserVO;

import java.util.List;

public interface UserService {

    void sendCode(String username);

    List<UserAreaDTO> listUserAreas(ConditionVO conditionVO);

    void register(UserVO userVO);

    void updatePassword(UserVO userVO);

    User getUserByUsername(String userName);

    PageResultDTO<UserAdminDTO> listUsers(ConditionVO condition);

    UserDetailsDTO qqLogin(QQLoginVO qqLoginVO);

}
