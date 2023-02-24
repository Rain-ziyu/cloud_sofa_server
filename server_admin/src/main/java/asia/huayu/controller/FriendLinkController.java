package asia.huayu.controller;

import asia.huayu.annotation.OptLog;
import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.FriendLinkAdminDTO;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.FriendLinkVO;
import asia.huayu.service.FriendLinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static asia.huayu.constant.OptTypeConstant.DELETE;
import static asia.huayu.constant.OptTypeConstant.SAVE_OR_UPDATE;

@Tag(name = "友链模块")
@RestController
public class FriendLinkController {

    @Autowired
    private FriendLinkService friendLinkService;


    @Operation(summary = "查看后台友链列表")
    @GetMapping("/links")
    public Result<PageResultDTO<FriendLinkAdminDTO>> listFriendLinkDTO(ConditionVO conditionVO) {
        return Result.OK(friendLinkService.listFriendLinksAdmin(conditionVO));
    }

    @OptLog(optType = SAVE_OR_UPDATE)
    @Operation(summary = "保存或修改友链")
    @PostMapping("/links")
    public Result<?> saveOrUpdateFriendLink(@Valid @RequestBody FriendLinkVO friendLinkVO) {
        friendLinkService.saveOrUpdateFriendLink(friendLinkVO);
        return Result.OK();
    }

    @OptLog(optType = DELETE)
    @Operation(summary = "删除友链")
    @DeleteMapping("/links")
    public Result<?> deleteFriendLink(@RequestBody List<Integer> linkIdList) {
        friendLinkService.removeByIds(linkIdList);
        return Result.OK();
    }
}
