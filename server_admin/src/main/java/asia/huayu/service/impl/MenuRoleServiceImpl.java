package asia.huayu.service.impl;

import asia.huayu.auth.entity.Permission;
import asia.huayu.auth.helper.PermissionHelper;
import asia.huayu.entity.MenuRole;
import asia.huayu.mapper.MenuRoleMapper;
import asia.huayu.service.MenuRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RainZiYu
 * @Date 2023/2/13
 */
@Service
public class MenuRoleServiceImpl implements MenuRoleService {
    @Autowired
    private MenuRoleMapper menuRoleMapper;

    @Override
    public List<Permission> getAllMenuRole() {
        List<MenuRole> allMenuAndRole = menuRoleMapper.getAllMenuAndRole();
        List<Permission> al = new ArrayList<>(allMenuAndRole);
        List<Permission> bulid = PermissionHelper.bulid(al).get(0).getChildren();
        return bulid;
    }
}
