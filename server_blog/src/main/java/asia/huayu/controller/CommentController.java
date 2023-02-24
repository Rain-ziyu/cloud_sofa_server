package asia.huayu.controller;

import asia.huayu.annotation.OptLog;
import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.CommentDTO;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.dto.ReplyDTO;
import asia.huayu.model.vo.CommentVO;
import asia.huayu.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static asia.huayu.constant.OptTypeConstant.SAVE;

@Tag(name = "评论模块")
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @OptLog(optType = SAVE)
    @Operation(summary = "添加评论")
    @PostMapping("/comments/save")
    public Result<?> saveComment(@Valid @RequestBody CommentVO commentVO) {
        commentService.saveComment(commentVO);
        return Result.OK();
    }

    @Operation(summary = "获取评论")
    @GetMapping("/comments")
    public Result<PageResultDTO<CommentDTO>> getComments(CommentVO commentVO) {
        return Result.OK(commentService.listComments(commentVO));
    }

    @Operation(summary = "根据commentId获取回复")
    @GetMapping("/comments/{commentId}/replies")
    public Result<List<ReplyDTO>> listRepliesByCommentId(@PathVariable("commentId") Integer commentId) {
        return Result.OK(commentService.listRepliesByCommentId(commentId));
    }

    @Operation(summary = "获取前六个评论")
    @GetMapping("/comments/topSix")
    public Result<List<CommentDTO>> listTopSixComments() {
        return Result.OK(commentService.listTopSixComments());
    }


}
