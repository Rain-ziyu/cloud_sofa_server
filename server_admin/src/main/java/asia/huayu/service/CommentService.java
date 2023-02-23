package asia.huayu.service;

import asia.huayu.entity.Comment;
import asia.huayu.model.dto.CommentAdminDTO;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.ReviewVO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface CommentService extends IService<Comment> {

    PageResultDTO<CommentAdminDTO> listCommentsAdmin(ConditionVO conditionVO);

    void updateCommentsReview(ReviewVO reviewVO);

}
