package asia.huayu.mapper;

import asia.huayu.entity.MenuRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author RainZiYu
 * @Date 2023/2/13
 */
@Mapper
public interface MenuRoleMapper {
    public List<MenuRole> getAllMenuAndRole();
}
