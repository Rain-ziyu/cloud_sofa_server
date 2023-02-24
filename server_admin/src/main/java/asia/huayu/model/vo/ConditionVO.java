package asia.huayu.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "查询条件")
public class ConditionVO {

    @Schema(description = "current", title = "页码", type = "Long")
    private Long current;

    @Schema(description = "size", title = "条数", type = "Long")
    private Long size;

    @Schema(description = "keywords", title = "搜索内容", type = "String")
    private String keywords;

    @Schema(description = "categoryId", title = "分类id", type = "Integer")
    private Integer categoryId;

    @Schema(description = "tagId", title = "标签id", type = "Integer")
    private Integer tagId;

    @Schema(description = "albumId", title = "相册id", type = "Integer")
    private Integer albumId;

    @Schema(description = "loginType", title = "登录类型", type = "Integer")
    private Integer loginType;

    @Schema(description = "type", title = "类型", type = "Integer")
    private Integer type;

    @Schema(description = "status", title = "状态", type = "Integer")
    private Integer status;

    @Schema(description = "startTime", title = "开始时间", type = "LocalDateTime")
    private LocalDateTime startTime;

    @Schema(description = "endTime", title = "结束时间", type = "LocalDateTime")
    private LocalDateTime endTime;

    @Schema(description = "isDelete", title = "是否删除", type = "Integer")
    private Integer isDelete;

    @Schema(description = "isReview", title = "是否审核", type = "Integer")
    private Integer isReview;

    @Schema(description = "isTop", title = "是否置顶", type = "Integer")
    private Integer isTop;

    @Schema(description = "isFeatured", title = "是否推荐", type = "Integer")
    private Integer isFeatured;


}
