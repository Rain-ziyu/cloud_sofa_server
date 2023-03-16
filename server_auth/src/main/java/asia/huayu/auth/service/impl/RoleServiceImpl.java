package asia.huayu.auth.service.impl;


import asia.huayu.auth.entity.Role;
import asia.huayu.auth.entity.UserRole;
import asia.huayu.auth.mapper.RoleMapper;
import asia.huayu.auth.service.RoleService;
import asia.huayu.auth.service.UserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleService userRoleService;

    @Override
    public List<String> selectRoleByUserId(String id) {
        // 根据用户id拥有的角色id
        List<UserRole> userRoleList = userRoleService.list(new QueryWrapper<UserRole>().eq("user_id", id).select("role_id"));
        List<String> roleIdList = userRoleList.stream().map(item -> item.getRoleId()).collect(Collectors.toList());
        List<String> roleList = new ArrayList<>();
        if (roleIdList.size() > 0) {
            roleList = roleMapper.getRoleListByID(roleIdList);
        }
        return roleList;
    }
}
