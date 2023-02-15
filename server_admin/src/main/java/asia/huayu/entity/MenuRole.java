package asia.huayu.entity;

import asia.huayu.auth.entity.Permission;
import lombok.Data;

import java.util.List;

/**
 * @author RainZiYu
 * @Date 2023/2/13
 * 角色菜单 用于查询出所有菜单并且携带对应的角色信息
 */
@Data
public class MenuRole extends Permission {

    private List<String> roles;

}
