package asia.huayu.auth.service;

import asia.huayu.auth.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
public interface PermissionService extends IService<Permission> {


    /**
     * 方法selectPermissionValueByUserId作用为：
     * 根据用户id获取用户接口权限
     *
     * @param id
     * @return java.util.List<java.lang.String>
     * @throws
     * @author RainZiYu
     */

    List<String> selectPermissionValueByUserId(String id);

}
