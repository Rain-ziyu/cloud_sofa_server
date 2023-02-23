package asia.huayu.service;

import asia.huayu.entity.UserInfo;
import asia.huayu.model.dto.UserInfoDTO;
import asia.huayu.model.vo.EmailVO;
import asia.huayu.model.vo.SubscribeVO;
import asia.huayu.model.vo.UserInfoVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface UserInfoService extends IService<UserInfo> {

    void updateUserInfo(UserInfoVO userInfoVO);

    String updateUserAvatar(MultipartFile file);

    void saveUserEmail(EmailVO emailVO);

    void updateUserSubscribe(SubscribeVO subscribeVO);

    UserInfoDTO getUserInfoById(Integer id);

    UserInfo getUserInfoByName(String username);

    UserInfoDTO getUserInfo();
}
