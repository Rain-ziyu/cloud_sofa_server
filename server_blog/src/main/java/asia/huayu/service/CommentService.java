package asia.huayu.service;

import asia.huayu.entity.Comment;
import asia.huayu.model.dto.CommentAdminDTO;
import asia.huayu.model.dto.CommentDTO;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.dto.ReplyDTO;
import asia.huayu.model.vo.CommentVO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.ReviewVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CommentService extends IService<Comment> {

    void saveComment(CommentVO commentVO);

    PageResultDTO<CommentDTO> listComments(CommentVO commentVO);

    List<ReplyDTO> listRepliesByCommentId(Integer commentId);

    List<CommentDTO> listTopSixComments();

    PageResultDTO<CommentAdminDTO> listCommentsAdmin(ConditionVO conditionVO);

    void updateCommentsReview(ReviewVO reviewVO);

}
