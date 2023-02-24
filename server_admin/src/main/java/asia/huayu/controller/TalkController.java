package asia.huayu.controller;

import asia.huayu.annotation.OptLog;
import asia.huayu.common.entity.Result;
import asia.huayu.enums.FilePathEnum;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.dto.TalkAdminDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.TalkVO;
import asia.huayu.service.TalkService;
import asia.huayu.strategy.context.UploadStrategyContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static asia.huayu.constant.OptTypeConstant.*;

@Tag(name = "说说模块")
@RestController
public class TalkController {

    @Autowired
    private TalkService talkService;

    @Autowired
    private UploadStrategyContext uploadStrategyContext;


    @OptLog(optType = UPLOAD)
    @Operation(summary = "上传说说图片")
    @Parameter(name = "file", description = "说说图片", required = true)
    @PostMapping("/talks/images")
    public Result<String> saveTalkImages(MultipartFile file) {
        return Result.OK(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.TALK.getPath()));
    }

    @OptLog(optType = SAVE_OR_UPDATE)
    @Operation(summary = "保存或修改说说")
    @PostMapping("/talks")
    public Result<?> saveOrUpdateTalk(@Valid @RequestBody TalkVO talkVO) {
        talkService.saveOrUpdateTalk(talkVO);
        return Result.OK();
    }

    @OptLog(optType = DELETE)
    @Operation(summary = "删除说说")
    @DeleteMapping("/talks")
    public Result<?> deleteTalks(@RequestBody List<Integer> talkIds) {
        talkService.deleteTalks(talkIds);
        return Result.OK();
    }

    @Operation(summary = "查看后台说说")
    @GetMapping("/talks")
    public Result<PageResultDTO<TalkAdminDTO>> listBackTalks(ConditionVO conditionVO) {
        return Result.OK(talkService.listBackTalks(conditionVO));
    }

    @Operation(summary = "根据id查看后台说说")
    @Parameter(name = "talkId", description = "说说id", required = true)
    @GetMapping("/talks/{talkId}")
    public Result<TalkAdminDTO> getBackTalkById(@PathVariable("talkId") Integer talkId) {
        return Result.OK(talkService.getBackTalkById(talkId));
    }

}
