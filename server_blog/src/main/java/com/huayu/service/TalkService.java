package com.huayu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huayu.entity.Talk;
import com.huayu.model.dto.PageResultDTO;
import com.huayu.model.dto.TalkAdminDTO;
import com.huayu.model.dto.TalkDTO;
import com.huayu.model.vo.ConditionVO;
import com.huayu.model.vo.TalkVO;

import java.util.List;


public interface TalkService extends IService<Talk> {

    PageResultDTO<TalkDTO> listTalks();

    TalkDTO getTalkById(Integer talkId);

    void saveOrUpdateTalk(TalkVO talkVO);

    void deleteTalks(List<Integer> talkIdList);

    PageResultDTO<TalkAdminDTO> listBackTalks(ConditionVO conditionVO);

    TalkAdminDTO getBackTalkById(Integer talkId);

}
