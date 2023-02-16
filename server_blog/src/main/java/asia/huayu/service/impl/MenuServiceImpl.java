package asia.huayu.service.impl;

import asia.huayu.auth.entity.Permission;
import asia.huayu.auth.entity.RolePermission;
import asia.huayu.auth.mapper.PermissionMapper;
import asia.huayu.auth.mapper.RolePermissionMapper;
import asia.huayu.constant.CommonConstant;
import asia.huayu.entity.User;
import asia.huayu.exception.BizException;
import asia.huayu.mapper.MenuMapper;
import asia.huayu.mapper.UserMapper;
import asia.huayu.model.dto.LabelOptionDTO;
import asia.huayu.model.dto.MenuDTO;
import asia.huayu.model.dto.UserMenuDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.IsHiddenVO;
import asia.huayu.model.vo.MenuVO;
import asia.huayu.service.MenuService;
import asia.huayu.util.BeanCopyUtil;
import asia.huayu.util.UserUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Permission> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<MenuDTO> listMenus(ConditionVO conditionVO) {
        List<Permission> permssions = permissionMapper.selectList(new LambdaQueryWrapper<Permission>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), Permission::getName, conditionVO.getKeywords()));
        //  取出所有父节点
        List<Permission> catalogs = listCatalogs(permssions);
        // 将所有子节点存储到 key为父节点id 的map
        Map<Integer, List<Permission>> childrenMap = getMenuMap(permssions);
        List<MenuDTO> menuDTOs = catalogs.stream().map(item -> {
            MenuDTO menuDTO = BeanCopyUtil.copyObject(item, MenuDTO.class);
            List<MenuDTO> list = BeanCopyUtil.copyList(childrenMap.get(item.getId()), MenuDTO.class).stream()
                    .sorted(Comparator.comparing(MenuDTO::getRank))
                    .collect(Collectors.toList());
            // 将子节点放入父结点中
            menuDTO.setChildren(list);
            childrenMap.remove(item.getId());
            return menuDTO;
        }).sorted(Comparator.comparing(MenuDTO::getRank)).collect(Collectors.toList());
        // 如果子结点中仍有值
        if (CollectionUtils.isNotEmpty(childrenMap)) {
            List<Permission> childrenList = new ArrayList<>();
            childrenMap.values().forEach(childrenList::addAll);
            List<MenuDTO> childrenDTOList = childrenList.stream()
                    .map(item -> BeanCopyUtil.copyObject(item, MenuDTO.class))
                    .sorted(Comparator.comparing(MenuDTO::getRank))
                    .collect(Collectors.toList());
            menuDTOs.addAll(childrenDTOList);
        }
        return menuDTOs;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateMenu(MenuVO menuVO) {
        Permission permssion = BeanCopyUtil.copyObject(menuVO, Permission.class);
        this.saveOrUpdate(permssion);
    }

    @Override
    public void updateMenuIsHidden(IsHiddenVO isHiddenVO) {
        Permission permssion = Permission.builder().status(isHiddenVO.getStatus()).id(isHiddenVO.getId()).build();
        permissionMapper.updateById(permssion);
    }

    @Override
    public void deleteMenu(Integer menuId) {
        Long count = rolePermissionMapper.selectCount(new LambdaQueryWrapper<RolePermission>()
                .eq(RolePermission::getPermissionId, menuId));
        if (count > 0) {
            throw new BizException("菜单下有角色关联");
        }
        List<Integer> menuIds = permissionMapper.selectList(new LambdaQueryWrapper<Permission>()
                        .select(Permission::getId)
                        .eq(Permission::getParentId, menuId))
                .stream()
                .map(Permission::getId)
                .collect(Collectors.toList());
        menuIds.add(menuId);
        permissionMapper.deleteBatchIds(menuIds);
    }

    @Override
    public List<LabelOptionDTO> listMenuOptions() {
        List<Permission> permssions = permissionMapper.selectList(new LambdaQueryWrapper<Permission>()
                .select(Permission::getId, Permission::getName, Permission::getParentId, Permission::getRank));
        List<Permission> catalogs = listCatalogs(permssions);
        Map<Integer, List<Permission>> childrenMap = getMenuMap(permssions);
        return catalogs.stream().map(item -> {
            List<LabelOptionDTO> list = new ArrayList<>();
            List<Permission> children = childrenMap.get(item.getId());
            if (CollectionUtils.isNotEmpty(children)) {
                list = children.stream()
                        .sorted(Comparator.comparing(Permission::getRank))
                        .map(permssion -> LabelOptionDTO.builder()
                                .id(permssion.getId())
                                .label(permssion.getName())
                                .build())
                        .collect(Collectors.toList());
            }
            return LabelOptionDTO.builder()
                    .id(item.getId())
                    .label(item.getName())
                    .children(list)
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public List<UserMenuDTO> listUserMenus() {
        String name = UserUtil.getAuthentication().getName();
        User user = userMapper.getUserByUsername(name);
        List<Permission> permssions = permissionMapper.selectPermissionByUserId(user.getId());
        List<Permission> catalogs = listCatalogs(permssions);
        Map<Integer, List<Permission>> childrenMap = getMenuMap(permssions);
        return convertUserMenuList(catalogs, childrenMap);
    }

    private List<Permission> listCatalogs(List<Permission> permssions) {
        return permssions.stream()
                .filter(item -> Objects.isNull(item.getParentId()))
                .sorted(Comparator.comparing(Permission::getRank))
                .collect(Collectors.toList());
    }

    private Map<Integer, List<Permission>> getMenuMap(List<Permission> permssions) {
        return permssions.stream()
                .filter(item -> Objects.nonNull(item.getParentId()))
                .collect(Collectors.groupingBy(Permission::getParentId));
    }

    private List<UserMenuDTO> convertUserMenuList(List<Permission> catalogList, Map<Integer, List<Permission>> childrenMap) {
        return catalogList.stream().map(item -> {
            UserMenuDTO userMenuDTO = new UserMenuDTO();
            List<UserMenuDTO> list = new ArrayList<>();
            List<Permission> children = childrenMap.get(item.getId());
            if (CollectionUtils.isNotEmpty(children)) {
                userMenuDTO = BeanCopyUtil.copyObject(item, UserMenuDTO.class);
                list = children.stream()
                        .sorted(Comparator.comparing(Permission::getRank))
                        .map(permssion -> {
                            UserMenuDTO dto = BeanCopyUtil.copyObject(permssion, UserMenuDTO.class);
                            dto.setHidden(permssion.getStatus().equals(CommonConstant.TRUE));
                            return dto;
                        })
                        .collect(Collectors.toList());
            } else {
                userMenuDTO.setPath(item.getPath());
                userMenuDTO.setComponent(CommonConstant.COMPONENT);
                list.add(UserMenuDTO.builder()
                        .path("")
                        .name(item.getName())
                        .icon(item.getIcon())
                        .component(item.getComponent())
                        .build());
            }
            userMenuDTO.setHidden(item.getStatus().equals(CommonConstant.TRUE));
            userMenuDTO.setChildren(list);
            return userMenuDTO;
        }).collect(Collectors.toList());
    }

}
