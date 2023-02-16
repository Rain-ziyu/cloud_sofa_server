package com.huayu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huayu.entity.ExceptionLog;
import com.huayu.model.dto.ExceptionLogDTO;
import com.huayu.model.dto.PageResultDTO;
import com.huayu.model.vo.ConditionVO;

public interface ExceptionLogService extends IService<ExceptionLog> {

    PageResultDTO<ExceptionLogDTO> listExceptionLogs(ConditionVO conditionVO);

}
