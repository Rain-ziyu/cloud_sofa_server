package asia.huayu.model.dto;

import asia.huayu.model.vo.ConditionVO;
import lombok.Data;

import java.util.List;

/**
 * @author RainZiYu
 * @Date 2023/3/16
 */
@Data
public class ArticleIdAndFilterDTO {
    List<Integer> articleIds;
    ConditionVO conditionVO;
}
