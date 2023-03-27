package asia.huayu.auth.mapper;

import asia.huayu.auth.entity.ResourceRole;
import asia.huayu.auth.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 方法listResourceRoles作用为：
     * 查询所有接口对应的角色   可以匿名访问的不加载进来
     *
     * @param
     * @return java.util.List<asia.huayu.auth.entity.ResourceRole>
     * @throws
     * @author RainZiYu
     */
    List<ResourceRole> listResourceRoles();

    List<String> getRoleListByID(List<String> roleIdList);
}
