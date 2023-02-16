package asia.huayu.service;

import asia.huayu.entity.Resource;
import asia.huayu.model.dto.LabelOptionDTO;
import asia.huayu.model.dto.ResourceDTO;
import asia.huayu.model.vo.ConditionVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ResourceService extends IService<Resource> {

    void importSwagger();

/*     void saveOrUpdateResource(ResourceVO resourceVO); */

    void deleteResource(Integer resourceId);

    List<ResourceDTO> listResources(ConditionVO conditionVO);

    List<LabelOptionDTO> listResourceOption();

}
