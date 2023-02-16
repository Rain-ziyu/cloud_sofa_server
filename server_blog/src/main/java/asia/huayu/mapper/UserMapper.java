package asia.huayu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import asia.huayu.entity.User;
import asia.huayu.model.dto.UserAdminDTO;
import asia.huayu.model.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserMapper extends BaseMapper<User> {

    List<UserAdminDTO> listUsers(@Param("current") Long current, @Param("size") Long size, @Param("conditionVO") ConditionVO conditionVO);

    Integer countUser(@Param("conditionVO") ConditionVO conditionVO);

    User getUserByUsername(String userName);

}
