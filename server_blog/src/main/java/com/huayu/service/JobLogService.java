package com.huayu.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.huayu.entity.JobLog;
import com.huayu.model.dto.JobLogDTO;
import com.huayu.model.dto.PageResultDTO;
import com.huayu.model.vo.JobLogSearchVO;

import java.util.List;


public interface JobLogService extends IService<JobLog> {

    PageResultDTO<JobLogDTO> listJobLogs(JobLogSearchVO jobLogSearchVO);

    void deleteJobLogs(List<Integer> ids);

    void cleanJobLogs();

    List<String> listJobLogGroups();

}
