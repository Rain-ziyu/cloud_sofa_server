package asia.huayu.strategy;


import asia.huayu.model.dto.UserDetailsDTO;

public interface SocialLoginStrategy {

    UserDetailsDTO login(String data);

}
