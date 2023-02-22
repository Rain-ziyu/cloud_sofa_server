package asia.huayu.service;

import asia.huayu.auth.entity.Role;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.dto.RoleDTO;
import asia.huayu.model.dto.UserRoleDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.RoleVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface BlogRoleService extends IService<Role> {

    List<UserRoleDTO> listUserRoles();

    PageResultDTO<RoleDTO> listRoles(ConditionVO conditionVO);

    void saveOrUpdateRole(RoleVO roleVO);

    void deleteRoles(List<Integer> ids);

}
