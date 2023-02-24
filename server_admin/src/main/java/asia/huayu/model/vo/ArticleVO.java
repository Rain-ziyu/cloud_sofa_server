package asia.huayu.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "文章")
public class ArticleVO {

    @Schema(description = "id", title = "文章id", type = "Integer")
    private Integer id;

    @NotBlank(message = "文章标题不能为空")
    @Schema(description = "articleTitle", title = "文章标题", required = true, type = "String")
    private String articleTitle;

    @NotBlank(message = "文章内容不能为空")
    @Schema(description = "articleContent", title = "文章内容", required = true, type = "String")
    private String articleContent;

    @Schema(description = "articleCover", title = "文章缩略图", type = "String")
    private String articleCover;

    @Schema(description = "category", title = "文章分类", type = "Integer")
    private String categoryName;

    @Schema(description = "tagNameList", title = "文章标签", type = "List<Integer>")
    private List<String> tagNames;

    @Schema(description = "isTop", title = "是否置顶", type = "Integer")
    private Integer isTop;

    @Schema(description = "isFeatured", title = "是否推荐", type = "Integer")
    private Integer isFeatured;

    @Schema(description = "status", title = "文章状态", type = "String")
    private Integer status;

    @Schema(description = "type", title = "文章类型", type = "Integer")
    private Integer type;

    @Schema(description = "originalUrl", title = "原文链接", type = "String")
    private String originalUrl;

    @Schema(description = "password", title = "文章访问密码", type = "String")
    private String password;
}
