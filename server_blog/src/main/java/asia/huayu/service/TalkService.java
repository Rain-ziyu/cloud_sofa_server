package asia.huayu.service;

import asia.huayu.entity.Talk;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.dto.TalkDTO;
import com.baomidou.mybatisplus.extension.service.IService;


public interface TalkService extends IService<Talk> {

    PageResultDTO<TalkDTO> listTalks();

    TalkDTO getTalkById(Integer talkId);


}
