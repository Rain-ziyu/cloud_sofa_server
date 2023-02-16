package asia.huayu.controller;

import asia.huayu.annotation.AccessLimit;
import asia.huayu.annotation.OptLog;
import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.CommentAdminDTO;
import asia.huayu.model.dto.CommentDTO;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.dto.ReplyDTO;
import asia.huayu.model.vo.CommentVO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.ReviewVO;
import asia.huayu.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static asia.huayu.constant.OptTypeConstant.*;

@Api(tags = "评论模块")
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @AccessLimit(seconds = 60, maxCount = 3)
    @OptLog(optType = SAVE)
    @ApiOperation("添加评论")
    @PostMapping("/comments/save")
    public Result<?> saveComment(@Valid @RequestBody CommentVO commentVO) {
        commentService.saveComment(commentVO);
        return Result.OK();
    }

    @ApiOperation("获取评论")
    @GetMapping("/comments")
    public Result<PageResultDTO<CommentDTO>> getComments(CommentVO commentVO) {
        return Result.OK(commentService.listComments(commentVO));
    }

    @ApiOperation(value = "根据commentId获取回复")
    @GetMapping("/comments/{commentId}/replies")
    public Result<List<ReplyDTO>> listRepliesByCommentId(@PathVariable("commentId") Integer commentId) {
        return Result.OK(commentService.listRepliesByCommentId(commentId));
    }

    @ApiOperation("获取前六个评论")
    @GetMapping("/comments/topSix")
    public Result<List<CommentDTO>> listTopSixComments() {
        return Result.OK(commentService.listTopSixComments());
    }

    @ApiOperation(value = "查询后台评论")
    @GetMapping("/admin/comments")
    public Result<PageResultDTO<CommentAdminDTO>> listCommentBackDTO(ConditionVO conditionVO) {
        return Result.OK(commentService.listCommentsAdmin(conditionVO));
    }

    @OptLog(optType = UPDATE)
    @ApiOperation(value = "审核评论")
    @PutMapping("/admin/comments/review")
    public Result<?> updateCommentsReview(@Valid @RequestBody ReviewVO reviewVO) {
        commentService.updateCommentsReview(reviewVO);
        return Result.OK();
    }

    @OptLog(optType = DELETE)
    @ApiOperation(value = "删除评论")
    @DeleteMapping("/admin/comments")
    public Result<?> deleteComments(@RequestBody List<Integer> commentIdList) {
        commentService.removeByIds(commentIdList);
        return Result.OK();
    }

}
