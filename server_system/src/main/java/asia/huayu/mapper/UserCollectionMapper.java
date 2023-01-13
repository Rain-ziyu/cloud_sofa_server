package asia.huayu.mapper;

import asia.huayu.entity.UserCollection;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author User
* @description 针对表【user_collection(用户收藏)】的数据库操作Mapper
* @createDate 2023-01-12 16:28:26
* @Entity asia.huayu.entity.UserCollection
*/
@Mapper
public interface UserCollectionMapper extends BaseMapper<UserCollection> {

}




