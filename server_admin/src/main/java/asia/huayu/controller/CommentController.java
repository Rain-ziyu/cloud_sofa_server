package asia.huayu.controller;

import asia.huayu.annotation.OptLog;
import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.CommentAdminDTO;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.ReviewVO;
import asia.huayu.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static asia.huayu.constant.OptTypeConstant.DELETE;
import static asia.huayu.constant.OptTypeConstant.UPDATE;

@Tag(name = "评论模块")
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;


    @Operation(summary = "查询后台评论")
    @GetMapping("/comments")
    public Result<PageResultDTO<CommentAdminDTO>> listCommentBackDTO(ConditionVO conditionVO) {
        return Result.OK(commentService.listCommentsAdmin(conditionVO));
    }

    @OptLog(optType = UPDATE)
    @Operation(summary = "审核评论")
    @PutMapping("/comments/review")
    public Result<?> updateCommentsReview(@Valid @RequestBody ReviewVO reviewVO) {
        commentService.updateCommentsReview(reviewVO);
        return Result.OK();
    }

    @OptLog(optType = DELETE)
    @Operation(summary = "删除评论")
    @DeleteMapping("/comments")
    public Result<?> deleteComments(@RequestBody List<Integer> commentIdList) {
        commentService.removeByIds(commentIdList);
        return Result.OK();
    }

}
