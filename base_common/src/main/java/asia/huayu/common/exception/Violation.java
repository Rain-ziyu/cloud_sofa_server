package asia.huayu.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author RainZiYu
 * @Date 2023/2/7
 */
@Data
@AllArgsConstructor
public class Violation {

    private final String fieldName;

    private final String message;

    // ...
}