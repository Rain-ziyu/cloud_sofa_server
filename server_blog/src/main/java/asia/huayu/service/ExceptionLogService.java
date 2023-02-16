package asia.huayu.service;

import asia.huayu.entity.ExceptionLog;
import asia.huayu.model.dto.ExceptionLogDTO;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.vo.ConditionVO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ExceptionLogService extends IService<ExceptionLog> {

    PageResultDTO<ExceptionLogDTO> listExceptionLogs(ConditionVO conditionVO);

}
