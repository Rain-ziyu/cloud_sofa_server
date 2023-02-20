package asia.huayu.controller;

import asia.huayu.annotation.OptLog;
import asia.huayu.common.controller.base.BaseController;
import asia.huayu.common.entity.Result;
import asia.huayu.enums.FilePathEnum;
import asia.huayu.model.dto.*;
import asia.huayu.model.vo.*;
import asia.huayu.service.ArticleService;
import asia.huayu.strategy.context.ArticleImportStrategyContext;
import asia.huayu.strategy.context.UploadStrategyContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static asia.huayu.constant.OptTypeConstant.*;

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

    @ApiOperation("获取后台文章")
    @GetMapping("/admin/articles")
    public Result<PageResultDTO<ArticleAdminDTO>> listArticlesAdmin(ConditionVO conditionVO) {
        return Result.OK(articleService.listArticlesAdmin(conditionVO));
    }

    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation("保存和修改文章")
    @PostMapping("/admin/articles")
    public Result<?> saveOrUpdateArticle(@Valid @RequestBody ArticleVO articleVO) {
        articleService.saveOrUpdateArticle(articleVO);
        return Result.OK("保存成功");
    }

    @OptLog(optType = UPDATE)
    @ApiOperation("修改文章是否置顶和推荐")
    @PutMapping("/admin/articles/topAndFeatured")
    public Result<?> updateArticleTopAndFeatured(@Valid @RequestBody ArticleTopFeaturedVO articleTopFeaturedVO) {
        articleService.updateArticleTopAndFeatured(articleTopFeaturedVO);
        return Result.OK();
    }

    @ApiOperation("删除或者恢复文章")
    @PutMapping("/admin/articles")
    public Result<?> updateArticleDelete(@Valid @RequestBody DeleteVO deleteVO) {
        articleService.updateArticleDelete(deleteVO);
        return Result.OK();
    }

    @OptLog(optType = DELETE)
    @ApiOperation(value = "物理删除文章")
    @DeleteMapping("/admin/articles/delete")
    public Result<?> deleteArticles(@RequestBody List<Integer> articleIds) {
        articleService.deleteArticles(articleIds);
        return Result.OK();
    }

    @OptLog(optType = UPLOAD)
    @ApiOperation("上传文章图片")
    @ApiImplicitParam(name = "file", value = "文章图片", required = true, dataType = "MultipartFile")
    @PostMapping("/admin/articles/images")
    public Result<String> saveArticleImages(MultipartFile file) {
        return Result.OK(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.ARTICLE.getPath()));
    }

    @ApiOperation("根据id查看后台文章")
    @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "Integer")
    @GetMapping("/admin/articles/{articleId}")
    public Result<ArticleAdminViewDTO> getArticleBackById(@PathVariable("articleId") Integer articleId) {
        return Result.OK(articleService.getArticleByIdAdmin(articleId));
    }

    @OptLog(optType = UPLOAD)
    @ApiOperation(value = "导入文章")
    @PostMapping("/admin/articles/import")
    public Result<?> importArticles(MultipartFile file, @RequestParam(required = false) String type) {
        articleImportStrategyContext.importArticles(file, type);
        return Result.OK();
    }

    @OptLog(optType = EXPORT)
    @ApiOperation(value = "导出文章")
    @ApiImplicitParam(name = "articleIdList", value = "文章id", required = true, dataType = "List<Integer>")
    @PostMapping("/admin/articles/export")
    public Result<List<String>> exportArticles(@RequestBody List<Integer> articleIds) {
        return Result.OK(articleService.exportArticles(articleIds));
    }

    @ApiOperation(value = "搜索文章")
    @GetMapping("/articles/search")
    public Result<List<ArticleSearchDTO>> listArticlesBySearch(ConditionVO condition) {
        return Result.OK(articleService.listArticlesBySearch(condition));
    }

}
