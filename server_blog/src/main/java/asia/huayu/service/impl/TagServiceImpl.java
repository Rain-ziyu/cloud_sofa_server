package asia.huayu.service.impl;


import asia.huayu.common.entity.Result;
import asia.huayu.entity.Tag;
import asia.huayu.mapper.TagMapper;
import asia.huayu.model.dto.TagAdminDTO;
import asia.huayu.model.dto.TagDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.service.TagService;
import asia.huayu.service.TokenService;
import asia.huayu.service.feign.TagFeignService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private TagFeignService tagFeignService;
    @Autowired
    private TokenService tokenService;

    @Override
    public List<TagDTO> listTags() {
        return tagMapper.listTags();
    }

    @Override
    public List<TagDTO> listTopTenTags() {
        return tagMapper.listTopTenTags();
    }

    @Override
    public List<TagDTO> listTagsBySearch(ConditionVO condition) {
        String token = tokenService.getUserTokenOrSystemToken();
        Result<List<TagAdminDTO>> listResult = tagFeignService.listTagsBySearch(condition, token);
        List<TagAdminDTO> data = listResult.getData();
        List<TagDTO> tagDTOS = new ArrayList<>();
        data.stream().forEach(x -> {
            TagDTO build = TagDTO.builder().tagName(x.getTagName()).count(x.getArticleCount()).id(x.getId()).build();
            tagDTOS.add(build);
        });
        return tagDTOS;
    }


}

