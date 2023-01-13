package asia.huayu.common.controller.base;


import asia.huayu.common.entity.Result;

/**
 * @author User
 */
@FunctionalInterface
public interface ResultProcessor {
    /**
     * 方法<code>process</code>作用为：
     * 使用传递函数方式 统一封装controller的逻辑代码
     *
     * @param
     * @return asia.huayu.common.entity.Result
     * @throws
     * @author RainZiYu
     */
    Result process();
}
