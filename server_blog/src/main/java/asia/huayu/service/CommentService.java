package asia.huayu.service;

import asia.huayu.entity.Comment;
import asia.huayu.model.dto.CommentDTO;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.dto.ReplyDTO;
import asia.huayu.model.vo.CommentVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CommentService extends IService<Comment> {

    void saveComment(CommentVO commentVO);

    PageResultDTO<CommentDTO> listComments(CommentVO commentVO);

    List<ReplyDTO> listRepliesByCommentId(Integer commentId);

    List<CommentDTO> listTopSixComments();


}
