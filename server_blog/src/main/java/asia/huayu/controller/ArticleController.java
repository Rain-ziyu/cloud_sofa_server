package asia.huayu.controller;

import asia.huayu.common.controller.base.BaseController;
import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.*;
import asia.huayu.model.vo.ArticlePasswordVO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.service.ArticleService;
import asia.huayu.strategy.context.ArticleImportStrategyContext;
import asia.huayu.strategy.context.UploadStrategyContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "文章模块")
@Api(tags = "文章模块")
@RestController
public class ArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UploadStrategyContext uploadStrategyContext;

    @Autowired
    private ArticleImportStrategyContext articleImportStrategyContext;

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

    @ApiOperation("根据id获取文章")
    @GetMapping("/articles/{articleId}")
    public Result<ArticleDTO> getArticleById(@PathVariable("articleId") Integer articleId) {
        return Result.OK(articleService.getArticleById(articleId));
    }

    @ApiOperation("校验文章访问密码")
    @PostMapping("/articles/access")
    public Result<?> accessArticle(@Valid @RequestBody ArticlePasswordVO articlePasswordVO) {
        articleService.accessArticle(articlePasswordVO);
        return Result.OK();
    }

    @ApiOperation("根据标签id获取文章")
    @GetMapping("/articles/tagId")
    public Result<PageResultDTO<ArticleCardDTO>> listArticlesByTagId(@RequestParam Integer tagId) {
        return Result.OK(articleService.listArticlesByTagId(tagId));
    }

    @ApiOperation("获取所有文章归档")
    @GetMapping("/archives/all")
    public Result<PageResultDTO<ArchiveDTO>> listArchives() {
        return Result.OK(articleService.listArchives());
    }

    @ApiOperation(value = "搜索文章")
    @GetMapping("/articles/search")
    public Result<List<ArticleSearchDTO>> listArticlesBySearch(ConditionVO condition) {
        return Result.OK(articleService.listArticlesBySearch(condition));
    }

}
