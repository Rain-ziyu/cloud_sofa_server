package asia.huayu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import asia.huayu.entity.Article;
import asia.huayu.service.ArticleService;
import asia.huayu.mapper.ArticleMapper;
import org.springframework.stereotype.Service;

/**
* @author User
* @description 针对表【article(文章，用于存储文章内容)】的数据库操作Service实现
* @createDate 2023-01-12 16:28:26
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{

}




