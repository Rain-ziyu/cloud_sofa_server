package asia.huayu.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


/**
 * @author 花未眠
 * 后台文章
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListDTO {

    private Integer id;

    private String articleCover;

    private String articleTitle;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer viewsCount;

    private String categoryName;

    private List<TagDTO> tagDTOs;

    private Integer isTop;

    private Integer isFeatured;

    private Integer isDelete;

    private Integer status;

    private Integer type;

}
