package asia.huayu.auth.mapper;

import asia.huayu.security.entity.SecurityUserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author RainZiYu
 * @Date 2023/1/11
 */
@Mapper
public interface SecurityUserInfoMapper {
    SecurityUserInfo getSecurityUserInfoByName(String userName);

    SecurityUserInfo getSecurityUserInfoById(String id);
}
