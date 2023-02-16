package com.huayu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huayu.entity.Resource;
import com.huayu.model.dto.LabelOptionDTO;
import com.huayu.model.dto.ResourceDTO;
import com.huayu.model.vo.ConditionVO;

import java.util.List;

public interface ResourceService extends IService<Resource> {

    void importSwagger();

/*     void saveOrUpdateResource(ResourceVO resourceVO); */

    void deleteResource(Integer resourceId);

    List<ResourceDTO> listResources(ConditionVO conditionVO);

    List<LabelOptionDTO> listResourceOption();

}
