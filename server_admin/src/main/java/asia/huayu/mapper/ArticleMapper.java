package asia.huayu.mapper;

import asia.huayu.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author User
 * @description 针对表【article(文章，用于存储文章内容)】的数据库操作Mapper
 * @createDate 2023-01-12 16:28:26
 * @Entity asia.huayu.entity.Article
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}




