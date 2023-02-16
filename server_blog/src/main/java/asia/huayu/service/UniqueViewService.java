package asia.huayu.service;

import asia.huayu.entity.UniqueView;
import asia.huayu.model.dto.UniqueViewDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UniqueViewService extends IService<UniqueView> {

    List<UniqueViewDTO> listUniqueViews();

}
