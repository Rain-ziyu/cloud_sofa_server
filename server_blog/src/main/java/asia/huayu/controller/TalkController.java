package asia.huayu.controller;

import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.dto.TalkDTO;
import asia.huayu.service.TalkService;
import asia.huayu.strategy.context.UploadStrategyContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "说说模块")
@RestController
public class TalkController {

    @Autowired
    private TalkService talkService;

    @Autowired
    private UploadStrategyContext uploadStrategyContext;

    @Operation(summary = "查看说说列表")
    @GetMapping("/talks")
    public Result<PageResultDTO<TalkDTO>> listTalks() {
        return Result.OK(talkService.listTalks());
    }

    @Operation(summary = "根据id查看说说")
    @Parameter(name = "talkId", description = "说说id", required = true)
    @GetMapping("/talks/{talkId}")
    public Result<TalkDTO> getTalkById(@PathVariable("talkId") Integer talkId) {
        return Result.OK(talkService.getTalkById(talkId));
    }


}
