package com.huayu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huayu.entity.Role;
import com.huayu.model.dto.PageResultDTO;
import com.huayu.model.dto.RoleDTO;
import com.huayu.model.dto.UserRoleDTO;
import com.huayu.model.vo.ConditionVO;
import com.huayu.model.vo.RoleVO;

import java.util.List;

public interface RoleService extends IService<Role> {

    List<UserRoleDTO> listUserRoles();

    PageResultDTO<RoleDTO> listRoles(ConditionVO conditionVO);

    void saveOrUpdateRole(RoleVO roleVO);

    void deleteRoles(List<Integer> ids);

}
