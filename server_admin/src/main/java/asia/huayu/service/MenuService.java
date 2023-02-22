package asia.huayu.service;

import asia.huayu.auth.entity.Permission;
import asia.huayu.model.dto.LabelOptionDTO;
import asia.huayu.model.dto.MenuDTO;
import asia.huayu.model.dto.UserMenuDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.IsHiddenVO;
import asia.huayu.model.vo.MenuVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface MenuService extends IService<Permission> {

    List<MenuDTO> listMenus(ConditionVO conditionVO);

    void saveOrUpdateMenu(MenuVO menuVO);

    void updateMenuIsHidden(IsHiddenVO isHiddenVO);

    void deleteMenu(Integer menuId);

    List<LabelOptionDTO> listMenuOptions();

    List<UserMenuDTO> listUserMenus();

}
