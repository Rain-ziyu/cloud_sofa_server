package asia.huayu.service.impl;

import asia.huayu.entity.Article;
import asia.huayu.mapper.ArticleMapper;
import asia.huayu.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author User
 * @description 针对表【article(文章，用于存储文章内容)】的数据库操作Service实现
 * @createDate 2023-01-12 16:28:26
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {

}




