package asia.huayu.service.impl;

import asia.huayu.entity.UserRole;
import asia.huayu.mapper.UserRoleMapper;
import asia.huayu.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
