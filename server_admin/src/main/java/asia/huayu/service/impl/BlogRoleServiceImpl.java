package asia.huayu.service.impl;

import asia.huayu.auth.entity.Role;
import asia.huayu.auth.entity.RolePermission;
import asia.huayu.auth.entity.UserRole;
import asia.huayu.auth.mapper.UserRoleMapper;
import asia.huayu.auth.service.RolePermissionService;
import asia.huayu.common.exception.ServiceProcessException;
import asia.huayu.constant.CommonConstant;
import asia.huayu.entity.RoleResource;
import asia.huayu.mapper.BlogRoleMapper;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.dto.RoleDTO;
import asia.huayu.model.dto.UserRoleDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.RoleVO;
import asia.huayu.service.BlogRoleService;
import asia.huayu.service.RoleResourceService;
import asia.huayu.util.BeanCopyUtil;
import asia.huayu.util.PageUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class BlogRoleServiceImpl extends ServiceImpl<BlogRoleMapper, Role> implements BlogRoleService {

    @Autowired
    private BlogRoleMapper blogRoleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleResourceService roleResourceService;

    @Autowired
    private RolePermissionService rolePermissionService;

/*     @Autowired
    private FilterInvocationSecurityMetadataSourceImpl filterInvocationSecurityMetadataSource; */

    @Override
    public List<UserRoleDTO> listUserRoles() {
        List<Role> roleList = blogRoleMapper.selectList(new LambdaQueryWrapper<Role>()
                .select(Role::getId, Role::getRoleName));
        return BeanCopyUtil.copyList(roleList, UserRoleDTO.class);
    }

    @SneakyThrows
    @Override
    public PageResultDTO<RoleDTO> listRoles(ConditionVO conditionVO) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<Role>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), Role::getRoleName, conditionVO.getKeywords());
        CompletableFuture<Long> asyncCount = CompletableFuture.supplyAsync(() -> blogRoleMapper.selectCount(queryWrapper));
        List<RoleDTO> roleDTOs = blogRoleMapper.listRoles(PageUtil.getLimitCurrent(), PageUtil.getSize(), conditionVO);
        return new PageResultDTO<>(roleDTOs, asyncCount.get());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateRole(RoleVO roleVO) {
        Role role = Role.builder()
                .id(roleVO.getId())
                .roleName(roleVO.getRoleName())
                .isDisable(CommonConstant.FALSE)
                .build();
        saveOrUpdate(role);
        // 循环赋予资源列表
        if (Objects.nonNull(roleVO.getResourceIds())) {
            if (Objects.nonNull(roleVO.getId())) {
                roleResourceService.remove(new LambdaQueryWrapper<RoleResource>()
                        .eq(RoleResource::getRoleId, roleVO.getId()));
            }
            List<RoleResource> roleResourceList = roleVO.getResourceIds().stream()
                    .map(resourceId -> RoleResource.builder()
                            .roleId(role.getId())
                            .resourceId(resourceId)
                            .build())
                    .collect(Collectors.toList());
            roleResourceService.saveBatch(roleResourceList);
            /*             filterInvocationSecurityMetadataSource.clearDataSource(); */
        }
        // 循环赋予菜单权限
        if (Objects.nonNull(roleVO.getMenuIds())) {
            if (Objects.nonNull(roleVO.getId())) {
                rolePermissionService.remove(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRoleId, roleVO.getId()));
            }
            List<RolePermission> roleMenuList = roleVO.getMenuIds().stream()
                    .map(menuId -> RolePermission.builder()
                            .roleId(role.getId())
                            .permissionId(menuId)
                            .build())
                    .collect(Collectors.toList());
            rolePermissionService.saveBatch(roleMenuList);
        }
    }

    @Override
    public void deleteRoles(List<Integer> roleIdList) {
        Long count = userRoleMapper.selectCount(new LambdaQueryWrapper<UserRole>()
                .in(UserRole::getRoleId, roleIdList));
        if (count > 0) {
            throw new ServiceProcessException("角色下存在用户");
        }
        blogRoleMapper.deleteBatchIds(roleIdList);
    }

}
