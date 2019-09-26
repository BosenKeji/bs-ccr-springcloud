package cn.bosenkeji.service;


import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.schedulerx2.model.v20190430.*;

public interface JobService {

    CreateJobResponse createJob(String jobName) throws ClientException;
    DeleteJobResponse deleteJob(long jobId) throws ClientException;

    EnableJobResponse enableJob(long jobId) throws ClientException;
    DisableJobResponse disableJob(long jobId) throws ClientException;

    GetJobInfoResponse getJobInfo(long jobId) throws ClientException;
    UpdateJobResponse updateJob(UpdateJobResponse updateJobResponse);

    ExecuteJobResponse executeJob(long jobId) throws ClientException;


}
