package asia.huayu.service.impl;

import asia.huayu.entity.Talk;
import asia.huayu.entity.UserInfo;
import asia.huayu.mapper.TalkMapper;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.dto.TalkAdminDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.TalkVO;
import asia.huayu.service.TalkService;
import asia.huayu.service.UserInfoService;
import asia.huayu.util.BeanCopyUtil;
import asia.huayu.util.CommonUtil;
import asia.huayu.util.PageUtil;
import asia.huayu.util.UserUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class TalkServiceImpl extends ServiceImpl<TalkMapper, Talk> implements TalkService {

    @Autowired
    private TalkMapper talkMapper;
    @Autowired
    private UserInfoService userInfoService;

    @Override
    public void saveOrUpdateTalk(TalkVO talkVO) {
        Talk talk = BeanCopyUtil.copyObject(talkVO, Talk.class);
        String name = UserUtil.getAuthentication().getName();
        UserInfo userInfoByName = userInfoService.getUserInfoByName(name);
        talk.setUserId(userInfoByName.getId());
        this.saveOrUpdate(talk);
    }

    @Override
    public void deleteTalks(List<Integer> talkIds) {
        talkMapper.deleteBatchIds(talkIds);
    }

    @Override
    public PageResultDTO<TalkAdminDTO> listBackTalks(ConditionVO conditionVO) {
        Long count = talkMapper.selectCount(new LambdaQueryWrapper<Talk>()
                .eq(Objects.nonNull(conditionVO.getStatus()), Talk::getStatus, conditionVO.getStatus()));
        if (count == 0) {
            return new PageResultDTO<>();
        }
        List<TalkAdminDTO> talkDTOs = talkMapper.listTalksAdmin(PageUtil.getLimitCurrent(), PageUtil.getSize(), conditionVO);
        talkDTOs.forEach(item -> {
            if (Objects.nonNull(item.getImages())) {
                item.setImgs(CommonUtil.castList(JSON.parseObject(item.getImages(), List.class), String.class));
            }
        });
        return new PageResultDTO<>(talkDTOs, count);
    }

    @Override
    public TalkAdminDTO getBackTalkById(Integer talkId) {
        TalkAdminDTO talkBackDTO = talkMapper.getTalkByIdAdmin(talkId);
        if (Objects.nonNull(talkBackDTO.getImages())) {
            talkBackDTO.setImgs(CommonUtil.castList(JSON.parseObject(talkBackDTO.getImages(), List.class), String.class));
        }
        return talkBackDTO;
    }

}

