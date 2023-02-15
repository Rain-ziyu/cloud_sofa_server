package asia.huayu.security.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author RainZiYu
 * @Date 2023/2/9
 */
@Data
public class TokenDTO {
    String token;
    String refreshToken;
    Date expires;
}
