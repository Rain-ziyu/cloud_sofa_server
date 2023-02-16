package com.huayu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huayu.entity.Job;
import com.huayu.model.dto.JobDTO;
import com.huayu.model.dto.PageResultDTO;
import com.huayu.model.vo.JobRunVO;
import com.huayu.model.vo.JobSearchVO;
import com.huayu.model.vo.JobStatusVO;
import com.huayu.model.vo.JobVO;

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
