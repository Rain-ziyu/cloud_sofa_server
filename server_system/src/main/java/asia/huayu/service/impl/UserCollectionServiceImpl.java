package asia.huayu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import asia.huayu.entity.UserCollection;
import asia.huayu.service.UserCollectionService;
import asia.huayu.mapper.UserCollectionMapper;
import org.springframework.stereotype.Service;

/**
* @author User
* @description 针对表【user_collection(用户收藏)】的数据库操作Service实现
* @createDate 2023-01-12 16:28:26
*/
@Service
public class UserCollectionServiceImpl extends ServiceImpl<UserCollectionMapper, UserCollection>
    implements UserCollectionService{

}




