package asia.huayu.service;

import asia.huayu.model.dto.TheWordOfTheDayDTO;

/**
 * @author RainZiYu
 * @Date 2023/3/6
 */
public interface HitokotoHttpService {
    /**
     * 方法getTheWordOfTheDay作用为：
     * 调用一言接口获取每日一言  内置参数动漫、漫画
     *
     * @param
     * @return asia.huayu.model.dto.TheWordOfTheDayDTO
     * @throws
     * @author RainZiYu
     */
    public TheWordOfTheDayDTO getTheWordOfTheDay();
}
