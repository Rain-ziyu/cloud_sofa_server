package asia.huayu.controller;

import asia.huayu.annotation.OptLog;
import asia.huayu.common.controller.base.BaseController;
import asia.huayu.common.entity.Result;
import asia.huayu.enums.FilePathEnum;
import asia.huayu.model.dto.ArticleAdminDTO;
import asia.huayu.model.dto.ArticleAdminViewDTO;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.vo.ArticleTopFeaturedVO;
import asia.huayu.model.vo.ArticleVO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.DeleteVO;
import asia.huayu.service.ArticleService;
import asia.huayu.strategy.context.ArticleImportStrategyContext;
import asia.huayu.strategy.context.UploadStrategyContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static asia.huayu.constant.OptTypeConstant.*;

@Tag(name = "文章模块")
@RestController
public class ArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UploadStrategyContext uploadStrategyContext;

    @Autowired
    private ArticleImportStrategyContext articleImportStrategyContext;


    @Operation(summary = "获取后台文章")
    @GetMapping("/articles")
    public Result<PageResultDTO<ArticleAdminDTO>> listArticlesAdmin(ConditionVO conditionVO) {
        return Result.OK(articleService.listArticlesAdmin(conditionVO));
    }

    @OptLog(optType = SAVE_OR_UPDATE)
    @Operation(summary = "保存和修改文章")
    @PostMapping("/articles")
    public Result<?> saveOrUpdateArticle(@Valid @RequestBody ArticleVO articleVO) {
        articleService.saveOrUpdateArticle(articleVO);
        return Result.OK("保存成功");
    }

    @OptLog(optType = UPDATE)
    @Operation(summary = "修改文章是否置顶和推荐")
    @PutMapping("/articles/topAndFeatured")
    public Result<?> updateArticleTopAndFeatured(@Valid @RequestBody ArticleTopFeaturedVO articleTopFeaturedVO) {
        articleService.updateArticleTopAndFeatured(articleTopFeaturedVO);
        return Result.OK();
    }

    @Operation(summary = "删除或者恢复文章")
    @PutMapping("/articles")
    public Result<?> updateArticleDelete(@Valid @RequestBody DeleteVO deleteVO) {
        articleService.updateArticleDelete(deleteVO);
        return Result.OK();
    }

    @OptLog(optType = DELETE)
    @Operation(summary = "物理删除文章")
    @DeleteMapping("/articles/delete")
    public Result<?> deleteArticles(@RequestBody List<Integer> articleIds) {
        articleService.deleteArticles(articleIds);
        return Result.OK();
    }

    @OptLog(optType = UPLOAD)
    @Operation(summary = "上传文章图片")
    @Parameter(name = "file", description = "文章图片", required = true)
    @PostMapping("/articles/images")
    public Result<String> saveArticleImages(MultipartFile file) {
        return Result.OK(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.ARTICLE.getPath()));
    }

    @Operation(summary = "根据id查看后台文章")
    @Parameter(name = "articleId", description = "文章id", required = true)
    @GetMapping("/articles/{articleId}")
    public Result<ArticleAdminViewDTO> getArticleBackById(@PathVariable("articleId") Integer articleId) {
        return Result.OK(articleService.getArticleByIdAdmin(articleId));
    }

    @OptLog(optType = UPLOAD)
    @Operation(summary = "导入文章")
    @PostMapping("/articles/import")
    public Result<?> importArticles(MultipartFile file, @RequestParam(required = false) String type) {
        articleImportStrategyContext.importArticles(file, type);
        return Result.OK();
    }

    @OptLog(optType = EXPORT)
    @Operation(summary = "导出文章")
    @Parameter(name = "articleIdList", description = "文章id", required = true)
    @PostMapping("/articles/export")
    public Result<List<String>> exportArticles(@RequestBody List<Integer> articleIds) {
        return Result.OK(articleService.exportArticles(articleIds));
    }


}
