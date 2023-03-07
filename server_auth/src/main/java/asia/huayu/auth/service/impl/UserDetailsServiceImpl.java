package asia.huayu.auth.service.impl;


import asia.huayu.auth.service.RoleService;
import asia.huayu.auth.service.SecurityUserInfoService;
import asia.huayu.security.entity.SecurityUser;
import asia.huayu.security.entity.SecurityUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 自定义实现用户的加载方式以及权限
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SecurityUserInfoService securityUserInfoService;
    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询数据
        SecurityUserInfo user = securityUserInfoService.selectByUsername(username);
        // 判断
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        // 根据用户id查询用户接口权限列表
        List<String> permissionValueList = roleService.selectRoleByUserId(String.valueOf(user.getId()));
        SecurityUser securityUser = new SecurityUser();
        // 设置当前SecurityUserInfo为用户信息
        securityUser.setSecurityUserInfo(user);
        securityUser.setPermissionValueList(permissionValueList);
        return securityUser;
    }


}
