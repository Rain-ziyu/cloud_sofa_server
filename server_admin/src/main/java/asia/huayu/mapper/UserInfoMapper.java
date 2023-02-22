package asia.huayu.mapper;

import asia.huayu.entity.UserInfo;
import asia.huayu.model.dto.UserInfoDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author User
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    UserInfoDTO selectDTOById(Integer id);
}
