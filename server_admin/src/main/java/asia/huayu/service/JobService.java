package asia.huayu.service;

import asia.huayu.entity.Job;
import asia.huayu.model.dto.JobDTO;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.vo.JobRunVO;
import asia.huayu.model.vo.JobSearchVO;
import asia.huayu.model.vo.JobStatusVO;
import asia.huayu.model.vo.JobVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface JobService extends IService<Job> {

    void saveJob(JobVO jobVO);

    void updateJob(JobVO jobVO);

    void deleteJobs(List<Integer> tagIds);

    JobDTO getJobById(Integer jobId);

    PageResultDTO<JobDTO> listJobs(JobSearchVO jobSearchVO);

    void updateJobStatus(JobStatusVO jobStatusVO);

    void runJob(JobRunVO jobRunVO);

    List<String> listJobGroups();

}
