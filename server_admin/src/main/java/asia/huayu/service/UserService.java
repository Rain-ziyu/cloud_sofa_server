package asia.huayu.service;

import asia.huayu.entity.User;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.dto.UserAdminDTO;
import asia.huayu.model.dto.UserAreaDTO;
import asia.huayu.model.vo.ConditionVO;

import java.util.List;

public interface UserService {


    List<UserAreaDTO> listUserAreas(ConditionVO conditionVO);

    User getUserByUsername(String userName);

    PageResultDTO<UserAdminDTO> listUsers(ConditionVO condition);


}
