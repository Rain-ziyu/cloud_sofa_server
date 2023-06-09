package asia.huayu.common.controller.base;


import asia.huayu.common.entity.Result;
import lombok.extern.slf4j.Slf4j;

@Slf4j
/**
 * @Author RainZiYu
 * Create By 2017/8/13
 */
public class BaseController {

    protected Result restProcessor(ResultProcessor processor) {
        Result result = null;
        result = processor.process();
        return result;
    }
}
