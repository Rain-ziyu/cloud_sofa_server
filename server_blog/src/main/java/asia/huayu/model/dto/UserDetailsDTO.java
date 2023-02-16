package asia.huayu.model.dto;

import asia.huayu.entity.User;
import asia.huayu.entity.UserInfo;
import asia.huayu.entity.UserLoginInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDTO {

    private User user;
    private UserInfo userInfo;
    private UserLoginInfo userLoginInfo;
    private String token;
    private String refreshToken;
}
