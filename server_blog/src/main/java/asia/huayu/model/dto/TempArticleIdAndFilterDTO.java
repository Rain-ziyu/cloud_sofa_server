package asia.huayu.model.dto;

import asia.huayu.model.vo.ConditionVO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author RainZiYu
 * @Date 2023/3/16
 * 用于接收临时文章id列表以及筛选条件
 */
@Data
public class TempArticleIdAndFilterDTO {
    @NotEmpty
    List<Long> tempArticleIds;
    ConditionVO conditionVO;
}
