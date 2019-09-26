package cn.bosenkeji.service.Impl;

import cn.bosenkeji.service.JobService;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.aliyuncs.schedulerx2.model.v20190430.*;

@Component
public class JobServiceImpl implements JobService {

    @Value("${regionId}")
    private String regionId;

    @Value("${accessKeyId}")
    private  String accessKeyId;

    @Value("${accessKeySecret}")
    private  String accessKeySecret;

    @Value("${productName}")
    private  String productName;

    @Value("${domain}")
    private  String domain;

    @Value("${namespace}")
    private  String namespace;

    @Value("${groupId}")
    private  String groupId;

    @Value("${jobType}")
    private String jobType;

    @Value("${className}")
    private String className;
    //单机模式 广播模式 等等
    @Value("${executeMode}")
    private String executeMode;

    @Value("${timeType}")
    private int timeType;

    @Value("${timeExpression}")
    private String timeExpression;

    @Override
    public CreateJobResponse createJob(String jobName) throws ClientException {

        DefaultAcsClient defaultAcsClient = getDefaultAcsClient();
        CreateJobRequest request=new CreateJobRequest();
        request.setNamespace(namespace);
        request.setGroupId(groupId);
        request.setJobType(jobType);
        request.setClassName(className);
        //createJobRequest.setJarUrl(jarUrl);
        request.setName(jobName);
        request.setDescription(jobName);
        request.setExecuteMode(executeMode);
        request.setTimeType(timeType);
        request.setTimeExpression(timeExpression);
        request.setParameters(jobName);



        CreateJobResponse response=defaultAcsClient.getAcsResponse(request);
        return response;
    }

    @Override
    public DeleteJobResponse deleteJob(long jobId) throws ClientException {

        DefaultAcsClient defaultAcsClient = getDefaultAcsClient();
        DeleteJobRequest request=new DeleteJobRequest();
        request.setGroupId(groupId);
        request.setNamespace(namespace);
        request.setJobId(jobId);
        DeleteJobResponse response=defaultAcsClient.getAcsResponse(request);
        return response;
    }

    @Override
    public EnableJobResponse enableJob(long jobId) throws ClientException {

        DefaultAcsClient defaultAcsClient = getDefaultAcsClient();
        EnableJobRequest enableJobRequest=new EnableJobRequest();
        enableJobRequest.setGroupId(groupId);
        enableJobRequest.setNamespace(namespace);
        enableJobRequest.setJobId(jobId);

        EnableJobResponse response=defaultAcsClient.getAcsResponse(enableJobRequest);
        return response;
    }

    @Override
    public DisableJobResponse disableJob(long jobId) throws ClientException {
        DefaultAcsClient defaultAcsClient = getDefaultAcsClient();
        DisableJobRequest request=new DisableJobRequest();
        request.setGroupId(groupId);
        request.setNamespace(namespace);
        request.setJobId(jobId);

        DisableJobResponse response=defaultAcsClient.getAcsResponse(request);
        return response;
    }

    @Override
    public GetJobInfoResponse getJobInfo(long jobId) throws ClientException {
        DefaultAcsClient defaultAcsClient = getDefaultAcsClient();
        GetJobInfoRequest request=new GetJobInfoRequest();
        request.setJobId(jobId);
        request.setNamespace(namespace);
        request.setGroupId(groupId);
        //发送请求
        GetJobInfoResponse response = defaultAcsClient.getAcsResponse(request);
        return response;
    }

    @Override
    public UpdateJobResponse updateJob(UpdateJobResponse updateJobResponse) {
        return null;
    }

    @Override
    public ExecuteJobResponse executeJob(long jobId) throws ClientException {

        DefaultAcsClient defaultAcsClient = getDefaultAcsClient();
        ExecuteJobRequest request=new ExecuteJobRequest();
        request.setGroupId(groupId);
        request.setNamespace(namespace);
        request.setJobId(jobId);
        ExecuteJobResponse response=defaultAcsClient.getAcsResponse(request);
        return response;
    }

    public DefaultAcsClient getDefaultAcsClient() {

        //构建 OpenApi 客户端
        DefaultProfile.addEndpoint(regionId, productName, domain);
        DefaultProfile defaultProfile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient defaultAcsClient = new DefaultAcsClient(defaultProfile);
        return defaultAcsClient;
    }

}
