package com.huayu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huayu.entity.OperationLog;
import com.huayu.model.dto.OperationLogDTO;
import com.huayu.model.dto.PageResultDTO;
import com.huayu.model.vo.ConditionVO;

public interface OperationLogService extends IService<OperationLog> {

    PageResultDTO<OperationLogDTO> listOperationLogs(ConditionVO conditionVO);

}
