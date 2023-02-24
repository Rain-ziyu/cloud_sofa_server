package asia.huayu.controller;

import asia.huayu.common.controller.base.BaseController;
import asia.huayu.common.entity.Result;
import asia.huayu.entity.CircleCaptchaDTO;
import asia.huayu.util.SystemEnums;
import asia.huayu.util.SystemValue;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.util.IdUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @author RainZiYu
 * @Date 2023/2/9
 */
@RestController
@RequestMapping("/captcha")
@Tag(name = "数字图形验证码模块")
public class GraphicVerificationCodeController extends BaseController {
    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping
    public Result getCaptcha() {
        return restProcessor(() -> {
            // 记住Hutool工具类生成验证码对象
            CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(120, 40, 4, 20);
            String code = circleCaptcha.getCode();
            CircleCaptchaDTO captchaDTO = new CircleCaptchaDTO();
            // 创建一个随机uuid 与该验证码绑定
            String uuid = IdUtil.fastSimpleUUID();
            captchaDTO.setUuid(uuid);
            captchaDTO.setImgBase64(circleCaptcha.getImageBase64());
            // 存储绑定信息到redis缓存
            redisTemplate.opsForValue().set(uuid, code, SystemValue.CAPTCHA_TIME_OUT, TimeUnit.MINUTES);
            return Result.OK(captchaDTO);
        });
    }

    @PostMapping
    public Result verifyCaptcha(@RequestBody CircleCaptchaDTO captchaDTO) {
        return restProcessor(() -> {
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            String code = valueOperations.get(captchaDTO.getUuid());
            if (captchaDTO.getUserInput().equals(code)) {
                return Result.OK(SystemEnums.CAPTCHA_VERIFY_SUCCESSFULLY.VALUE);
            } else {
                return Result.ERROR(SystemEnums.VERIFICATION_CODE_ERROR.VALUE);
            }
        });
    }
}
