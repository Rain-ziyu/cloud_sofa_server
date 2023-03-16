package asia.huayu.service.impl;

import asia.huayu.entity.TempArticle;
import asia.huayu.entity.User;
import asia.huayu.mapper.ArticleMapper;
import asia.huayu.mapper.TempArticleMapper;
import asia.huayu.service.TempArticleService;
import asia.huayu.service.UserService;
import asia.huayu.util.UserUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author User
 * @description 针对表【temp_article(用于存储临时的未登录用户的发布文章)】的数据库操作Service实现
 * @createDate 2023-03-14 18:07:07
 */
@Service
public class TempArticleServiceImpl extends ServiceImpl<TempArticleMapper, TempArticle>
        implements TempArticleService {
    /**
     * 方法bindTempArticle作用为：
     * 为临时文章绑定到真正注册登录的用户
     *
     * @author RainZiYu
     * @param tempArticleId
     * @throws
     * @return void
     */
    @Autowired
    private TempArticleMapper tempArticleMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindTempArticle(List<Long> tempArticleId) {
        List<Integer> articleIds = getArticleIds(tempArticleId);
        Authentication authentication = UserUtil.getAuthentication();
        String name = authentication.getName();
        User userByUsername = userService.getUserByUsername(name);
        articleMapper.updateArticleOwner(articleIds, userByUsername.getId());
    }

    @Override
    public List<Integer> getArticleIds(List<Long> tempArticleId) {
        List<TempArticle> tempArticles = tempArticleMapper.selectList(new LambdaQueryWrapper<TempArticle>().in(TempArticle::getId, tempArticleId));
        List<Integer> articleIds = new ArrayList<>();
        tempArticles.forEach(x -> {
            articleIds.add(x.getArticleId());
        });
        return articleIds;
    }
}




