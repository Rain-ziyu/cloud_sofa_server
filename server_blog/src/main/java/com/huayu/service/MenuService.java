package com.huayu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huayu.entity.Menu;
import com.huayu.model.dto.LabelOptionDTO;
import com.huayu.model.dto.MenuDTO;
import com.huayu.model.dto.UserMenuDTO;
import com.huayu.model.vo.ConditionVO;
import com.huayu.model.vo.IsHiddenVO;
import com.huayu.model.vo.MenuVO;

import java.util.List;

public interface MenuService extends IService<Menu> {

    List<MenuDTO> listMenus(ConditionVO conditionVO);

    void saveOrUpdateMenu(MenuVO menuVO);

    void updateMenuIsHidden(IsHiddenVO isHiddenVO);

    void deleteMenu(Integer menuId);

    List<LabelOptionDTO> listMenuOptions();

    List<UserMenuDTO> listUserMenus();

}
