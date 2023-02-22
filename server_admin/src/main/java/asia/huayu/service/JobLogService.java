package asia.huayu.service;


import asia.huayu.entity.JobLog;
import asia.huayu.model.dto.JobLogDTO;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.vo.JobLogSearchVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface JobLogService extends IService<JobLog> {

    PageResultDTO<JobLogDTO> listJobLogs(JobLogSearchVO jobLogSearchVO);

    void deleteJobLogs(List<Integer> ids);

    void cleanJobLogs();

    List<String> listJobLogGroups();

}
