package asia.huayu.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author RainZiYu
 * @Date 2023/3/16
 */
@Data
public class TempArticleListDTO {
    private String id;

    private String articleCover;

    private String articleTitle;

    private LocalDateTime createTime;

    private Integer viewsCount;

    private String categoryName;

    private List<TagDTO> tagDTOs;

    private Integer isTop;

    private Integer isFeatured;

    private Integer isDelete;

    private Integer status;

    private Integer type;

}
