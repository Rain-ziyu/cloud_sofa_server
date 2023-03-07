package asia.huayu.auth.service;


import asia.huayu.auth.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
public interface RoleService extends IService<Role> {



    List<String> selectRoleByUserId(String id);
}
