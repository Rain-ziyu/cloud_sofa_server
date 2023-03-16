package asia.huayu.model.dto;

import asia.huayu.model.vo.ConditionVO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author RainZiYu
 * @Date 2023/3/16
 */
@Data
public class ArticleIdAndFilterDTO {
    @NotBlank
    List<Integer> articleIds;
    ConditionVO conditionVO;
}
