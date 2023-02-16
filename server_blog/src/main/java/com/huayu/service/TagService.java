package com.huayu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huayu.entity.Tag;
import com.huayu.model.dto.PageResultDTO;
import com.huayu.model.dto.TagAdminDTO;
import com.huayu.model.dto.TagDTO;
import com.huayu.model.vo.ConditionVO;
import com.huayu.model.vo.TagVO;

import java.util.List;

public interface TagService extends IService<Tag> {

    List<TagDTO> listTags();

    List<TagDTO> listTopTenTags();

    PageResultDTO<TagAdminDTO> listTagsAdmin(ConditionVO conditionVO);

    List<TagAdminDTO> listTagsAdminBySearch(ConditionVO conditionVO);

    void saveOrUpdateTag(TagVO tagVO);

    void deleteTag(List<Integer> tagIds);

}
