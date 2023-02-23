package asia.huayu.service;

import asia.huayu.entity.Talk;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.dto.TalkAdminDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.TalkVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface TalkService extends IService<Talk> {

    void saveOrUpdateTalk(TalkVO talkVO);

    void deleteTalks(List<Integer> talkIdList);

    PageResultDTO<TalkAdminDTO> listBackTalks(ConditionVO conditionVO);

    TalkAdminDTO getBackTalkById(Integer talkId);

}
