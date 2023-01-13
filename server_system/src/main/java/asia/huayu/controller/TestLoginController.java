package asia.huayu.controller;

import asia.huayu.common.controller.base.BaseController;
import asia.huayu.common.entity.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author RainZiYu
 * @Date 2023/1/11
 */
@RestController
public class TestLoginController extends BaseController {
    @GetMapping("/api1")
    public Result get() {
        return Result.OK("访问么有问题");
    }
}
