package asia.huayu.service.impl;


import asia.huayu.entity.Tag;
import asia.huayu.mapper.TagMapper;
import asia.huayu.model.dto.TagDTO;
import asia.huayu.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;


    @Override
    public List<TagDTO> listTags() {
        return tagMapper.listTags();
    }

    @Override
    public List<TagDTO> listTopTenTags() {
        return tagMapper.listTopTenTags();
    }


}

