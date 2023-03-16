package asia.huayu.mapper;

import asia.huayu.entity.Article;
import asia.huayu.model.dto.ArticleListDTO;
import asia.huayu.model.dto.ArticleStatisticsDTO;
import asia.huayu.model.vo.ConditionVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ArticleMapper extends BaseMapper<Article> {


    Integer countArticleAdmins(@Param("conditionVO") ConditionVO conditionVO);

    Integer countArticleByUser(@Param("conditionVO") ConditionVO conditionVO, Integer userId);

    List<ArticleListDTO> listArticlesAdmin(@Param("current") Long current, @Param("size") Long size, @Param("conditionVO") ConditionVO conditionVO);

    List<ArticleListDTO> listArticlesByUser(@Param("current") Long current, @Param("size") Long size, @Param("conditionVO") ConditionVO conditionVO, Integer userId);

    List<ArticleStatisticsDTO> listArticleStatistics();

    List<ArticleListDTO> listArticlesById(@Param("current") Long current, @Param("size") Long size, List<Integer> articleIds, @Param("conditionVO") ConditionVO conditionVO);
}

