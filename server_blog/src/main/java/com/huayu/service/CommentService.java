package com.huayu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huayu.entity.Comment;
import com.huayu.model.dto.CommentAdminDTO;
import com.huayu.model.dto.CommentDTO;
import com.huayu.model.dto.PageResultDTO;
import com.huayu.model.dto.ReplyDTO;
import com.huayu.model.vo.CommentVO;
import com.huayu.model.vo.ConditionVO;
import com.huayu.model.vo.ReviewVO;

import java.util.List;

public interface CommentService extends IService<Comment> {

    void saveComment(CommentVO commentVO);

    PageResultDTO<CommentDTO> listComments(CommentVO commentVO);

    List<ReplyDTO> listRepliesByCommentId(Integer commentId);

    List<CommentDTO> listTopSixComments();

    PageResultDTO<CommentAdminDTO> listCommentsAdmin(ConditionVO conditionVO);

    void updateCommentsReview(ReviewVO reviewVO);

}
