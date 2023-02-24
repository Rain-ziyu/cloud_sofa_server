package asia.huayu.controller;

import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.FriendLinkDTO;
import asia.huayu.service.FriendLinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "友链模块")
@RestController
public class FriendLinkController {

    @Autowired
    private FriendLinkService friendLinkService;

    @Operation(summary = "查看友链列表")
    @GetMapping("/links")
    public Result<List<FriendLinkDTO>> listFriendLinks() {
        return Result.OK(friendLinkService.listFriendLinks());
    }

}
