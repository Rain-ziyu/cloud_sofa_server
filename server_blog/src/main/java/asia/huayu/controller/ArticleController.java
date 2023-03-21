package asia.huayu.controller;

import asia.huayu.common.controller.base.BaseController;
import asia.huayu.common.entity.Result;
import asia.huayu.constant.CommonConstant;
import asia.huayu.model.dto.*;
import asia.huayu.model.vo.ArticlePasswordVO;
import asia.huayu.model.vo.ArticleVO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.TempDeleteVO;
import asia.huayu.service.ArticleService;
import asia.huayu.service.TempArticleService;
import asia.huayu.service.TokenService;
import asia.huayu.service.feign.ArticleFeignService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;


@Tag(name = "文章模块")
@RestController
public class ArticleController extends BaseController {
    @Autowired
    private ArticleFeignService articleFeignService;
    @Autowired
    private ArticleService articleService;

    @Autowired
    private TokenService tokenService;
    @Autowired
    private TempArticleService tempArticleService;

    @Operation(summary = "获取置顶和推荐文章")
    @GetMapping("/articles/topAndFeatured")
    public Result<TopAndFeaturedArticlesDTO> listTopAndFeaturedArticles() {
        return restProcessor(() -> asia.huayu.common.entity.Result.OK(articleService.listTopAndFeaturedArticles()));
    }

    @Operation(summary = "获取所有文章")
    @GetMapping("/articles/all")
    public Result<PageResultDTO<ArticleCardDTO>> listArticles() {
        return Result.OK(articleService.listArticles());
    }

    @Operation(summary = "根据分类id获取文章")
    @GetMapping("/articles/categoryId")
    public Result<PageResultDTO<ArticleCardDTO>> getArticlesByCategoryId(@RequestParam Integer categoryId) {
        return Result.OK(articleService.listArticlesByCategoryId(categoryId));
    }

    @Operation(summary = "根据id获取文章")
    @GetMapping("/articles/{articleId}")
    public Result<ArticleDTO> getArticleById(@PathVariable("articleId") Integer articleId) {
        return Result.OK(articleService.getArticleById(articleId));
    }

    @Operation(summary = "校验文章访问密码")
    @PostMapping("/articles/access")
    public Result<?> accessArticle(@Valid @RequestBody ArticlePasswordVO articlePasswordVO) {
        articleService.accessArticle(articlePasswordVO);
        return Result.OK();
    }

    @Operation(summary = "根据标签id获取文章")
    @GetMapping("/articles/tagId")
    public Result<PageResultDTO<ArticleCardDTO>> listArticlesByTagId(@RequestParam Integer tagId) {
        return Result.OK(articleService.listArticlesByTagId(tagId));
    }

    @Operation(summary = "获取所有文章归档")
    @GetMapping("/archives/all")
    public Result<PageResultDTO<ArchiveDTO>> listArchives() {
        return Result.OK(articleService.listArchives());
    }

    @Operation(summary = "搜索文章")
    @GetMapping("/articles/search")
    public Result<List<ArticleSearchDTO>> listArticlesBySearch(ConditionVO condition) {
        return Result.OK(articleService.listArticlesBySearch(condition));
    }


    @Operation(summary = "用户发布文章")
    @PostMapping("/articles")
    public Result<String> saveOrUpdate(@Valid @RequestBody ArticleVO articleVO) {
        return restProcessor(() -> articleService.saveOrUpdateArticle(articleVO));
    }

    @Operation(summary = "获取用户发布文章")
    @GetMapping("/articles")
    public Result getUserArticles(ConditionVO conditionVO) {
        return restProcessor(() -> Result.OK(articleService.listArticlesByUser(conditionVO)));
    }

    @Operation(summary = "获取文章列表通过临时文章id")
    @PostMapping("/articles/tempId")
    public Result getUserArticlesByTempId(@Valid @RequestBody TempArticleIdAndFilterDTO tempArticleIdAndFilterDTO) {
        return restProcessor(() -> Result.OK(articleService.listArticlesByTempId(tempArticleIdAndFilterDTO.getTempArticleIds(), tempArticleIdAndFilterDTO.getConditionVO())));
    }

    @Operation(summary = "修改用户文章删除状态")
    @PutMapping("/articles")
    public Result updateArticlesDelete(@Valid @RequestBody TempDeleteVO deleteVO) {
        return restProcessor(() -> Result.OK((Object) articleService.updateArticleDelete(deleteVO)));
    }

    @Operation(summary = "彻底删除用户文章")
    @DeleteMapping("/articles/delete")
    public Result<?> deleteArticles(@RequestBody List<Long> tempArticleIds) {
        return Result.OK(articleService.deleteArticles(tempArticleIds));
    }

    /**
     * 方法getArticleBackById作用为：
     * 获取编辑文章页面需要的信息  如果是临时文章那么传递的是临时文章id 在线文章传递的是正常id
     *
     * @param articleId
     * @return asia.huayu.common.entity.Result<asia.huayu.model.dto.ArticleViewDTO>
     * @throws
     * @author RainZiYu
     */
    @Operation(summary = "根据文章id获取编辑文章所需信息")
    @GetMapping("/articles/edit/{articleId}")
    public Result<ArticleViewDTO> getArticleBackById(@PathVariable("articleId") Long articleId) {
        return restProcessor(() -> articleService.getArticleBackById(articleId));
    }
    @Operation(summary = "上传文章封面")
    @Parameter(name = "file", description = "文章图片", required = true)
    @PostMapping("/articles/images")
    public Result<String> saveArticleImages(MultipartFile file) {
        String token = tokenService.getUserTokenOrSystemToken();
        return restProcessor(() -> Result.OK(articleFeignService.saveArticleImages(file, token)));
    }

    /**
     * 方法作用为：
     * 绑定用户与临时文章
     *
     * @param
     * @return
     * @throws
     * @author RainZiYu
     */
    @Operation(summary = "绑定临时文章")
    @PostMapping("/binding/articles")
    public Result bindingTempArticles(@RequestBody List<Long> tempArticleId) {
        return restProcessor(() -> {
            tempArticleService.bindTempArticle(tempArticleId);
            return Result.OK(CommonConstant.BINDING_SUCCESS);
        });
    }

    @Operation(summary = "文章导出")
    @PostMapping("/articles/export")
    public Result<List<String>> exportArticles(@RequestBody List<Long> articleIds) {
        return restProcessor(() -> articleService.exportArticles(articleIds));
    }

    @Operation(summary = "导入文章")
    @PostMapping("/articles/import")
    public Result<?> importArticles(MultipartFile file, @RequestParam(required = false) String type) {
        return restProcessor(() -> {
            return articleService.importArticles(file, type);
        });
    }
}
