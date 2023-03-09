package asia.huayu.auth.service.impl;


import asia.huayu.auth.entity.Permission;
import asia.huayu.auth.mapper.PermissionMapper;
import asia.huayu.auth.service.PermissionService;
import asia.huayu.auth.service.SecurityUserInfoService;
import asia.huayu.security.entity.SecurityUserInfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {


    @Autowired
    private SecurityUserInfoService securityUserInfoService;


    /**
     * 方法selectPermissionValueByUserId作用为：
     * 根据用户id获取用户接口权限
     *
     * @param id
     * @return java.util.List<java.lang.String>
     * @throws
     * @author RainZiYu
     */
    @Override
    public List<String> selectPermissionValueByUserId(String id) {

        List<String> selectPermissionValueList = null;
        if (this.isSysAdmin(id)) {
            // 如果是系统管理员，获取所有权限
            selectPermissionValueList = baseMapper.selectAllPermissionValue();
        } else {
            selectPermissionValueList = baseMapper.selectPermissionValueByUserId(id);
        }
        return selectPermissionValueList;
    }



    /**
     * 判断用户是否系统管理员
     *
     * @param userId
     * @return
     */
    private boolean isSysAdmin(String userId) {
        SecurityUserInfo user = securityUserInfoService.getById(userId);
        return null != user && "admin".equals(user.getUsername());
    }



}
