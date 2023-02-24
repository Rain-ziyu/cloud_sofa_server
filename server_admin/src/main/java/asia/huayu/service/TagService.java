package asia.huayu.service;

import asia.huayu.entity.Tag;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.dto.TagAdminDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.TagVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface TagService extends IService<Tag> {


    PageResultDTO<TagAdminDTO> listTagsAdmin(ConditionVO conditionVO);

    List<TagAdminDTO> listTagsAdminBySearch(ConditionVO conditionVO);

    void saveOrUpdateTag(TagVO tagVO);

    void deleteTag(List<Integer> tagIds);

}
