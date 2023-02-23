package asia.huayu.service;

import asia.huayu.entity.User;
import asia.huayu.model.dto.UserDetailsDTO;
import asia.huayu.model.vo.QQLoginVO;
import asia.huayu.model.vo.UserVO;

public interface UserService {

    void sendCode(String username);

    void register(UserVO userVO);

    void updatePassword(UserVO userVO);

    User getUserByUsername(String userName);

    UserDetailsDTO qqLogin(QQLoginVO qqLoginVO);

}
