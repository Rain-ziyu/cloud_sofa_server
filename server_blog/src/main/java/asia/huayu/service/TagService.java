package asia.huayu.service;

import asia.huayu.entity.Tag;
import asia.huayu.model.dto.TagDTO;
import asia.huayu.model.vo.ConditionVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface TagService extends IService<Tag> {

    List<TagDTO> listTags();

    List<TagDTO> listTopTenTags();


    List<TagDTO> listTagsBySearch(ConditionVO condition);
}
