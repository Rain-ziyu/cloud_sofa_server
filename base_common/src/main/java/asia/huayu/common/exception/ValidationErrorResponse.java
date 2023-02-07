package asia.huayu.common.exception;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RainZiYu
 * @Date 2023/2/7
 */
@Data
public class ValidationErrorResponse {

    private List<Violation> violations = new ArrayList<>();

    // ...
}


