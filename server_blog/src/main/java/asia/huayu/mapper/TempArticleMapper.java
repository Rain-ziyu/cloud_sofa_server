package asia.huayu.mapper;

import asia.huayu.entity.TempArticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author User
 * @description 针对表【temp_article(用于存储临时的未登录用户的发布文章)】的数据库操作Mapper
 * @createDate 2023-03-14 18:07:07
 * @Entity asia.huayu.entity.TempArticle
 */
@Mapper
public interface TempArticleMapper extends BaseMapper<TempArticle> {

}




