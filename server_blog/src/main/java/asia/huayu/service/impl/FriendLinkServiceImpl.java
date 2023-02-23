package asia.huayu.service.impl;

import asia.huayu.entity.FriendLink;
import asia.huayu.mapper.FriendLinkMapper;
import asia.huayu.model.dto.FriendLinkDTO;
import asia.huayu.service.FriendLinkService;
import asia.huayu.util.BeanCopyUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink> implements FriendLinkService {

    @Autowired
    private FriendLinkMapper friendLinkMapper;

    @Override
    public List<FriendLinkDTO> listFriendLinks() {
        List<FriendLink> friendLinks = friendLinkMapper.selectList(null);
        return BeanCopyUtil.copyList(friendLinks, FriendLinkDTO.class);
    }


}
