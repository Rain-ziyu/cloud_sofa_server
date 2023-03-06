package asia.huayu.service.impl;

import asia.huayu.config.RestConfig;
import asia.huayu.model.dto.TheWordOfTheDayDTO;
import asia.huayu.service.HitokotoHttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

/**
 * @author RainZiYu
 * @Date 2023/3/6
 */
@Service
public class HitokotoHttpServiceImpl implements HitokotoHttpService {
    @Autowired
    RestConfig.RestService restService;
    @Value("${the-word-of-the-day-url:https://v1.hitokoto.cn}")
    private String theWordOfTheDayUrl;

    @Override
    public TheWordOfTheDayDTO getTheWordOfTheDay() {
        HttpHeaders httpHeaders = new HttpHeaders();
        String params = "/?c=a&c=b";
        // url参数编码
        ResponseEntity<TheWordOfTheDayDTO> result = restService.restTemplate.exchange(URI.create(theWordOfTheDayUrl + params),
                HttpMethod.GET, new HttpEntity<>(httpHeaders), TheWordOfTheDayDTO.class);
        return result.getBody();
    }
}
