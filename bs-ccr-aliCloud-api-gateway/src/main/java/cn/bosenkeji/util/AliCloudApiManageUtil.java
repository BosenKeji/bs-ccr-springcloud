package cn.bosenkeji.util;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.cloudapi.model.v20160714.*;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author CAJR
 * @create 2019/7/29 19:57
 */
@Component
public class AliCloudApiManageUtil {

    @Autowired
    IAcsClient client;

    @Value("${api.regionId}")
    private  String regionId ;

    @Value("${api.groupId}")
    private String groupId;

    @Value("${api.visibility}")
    private String visibility;

    @Value("${api.resultType}")
    private String resultType;

    @Value("${api.authType}")
    private String authType;

    @Value("${api.protocol}")
    private  String protocol;

    @Value("${api.serviceTimeout}")
    private  Integer serviceTimeout;

    @Value("${api.serviceAddress}")
    private String serviceAddress;

    @Value("${api.bodyFormat}")
    private String bodyFormat;


    /**
     * TODO 查询api分组信息
     * @return
     * @throws ClientException
     */
    public DescribeApiGroupResponse describeApiGroup() throws ClientException {
        DescribeApiGroupRequest request = new DescribeApiGroupRequest();
        request.setGroupId(groupId);

        DescribeApiGroupResponse response = client.getAcsResponse(request);

        return response;
    }

    /**
     * TODO 查询单个api信息
     * @param apiId
     * @return
     * @throws ClientException
     */
    public DescribeApiResponse describeApi(String apiId) throws ClientException {
        DescribeApiRequest request = new DescribeApiRequest();
        request.setApiId(apiId);
        request.setGroupId(groupId);

        return client.getAcsResponse(request);
    }

    public DescribeApisResponse describeApis(int pageNumber, int pageSize) throws ClientException {
        DescribeApisRequest describeApisRequest = new DescribeApisRequest();
        describeApisRequest.setPageSize(pageSize);
        describeApisRequest.setPageNumber(pageNumber);
        describeApisRequest.setGroupId(groupId);

        return client.getAcsResponse(describeApisRequest);
    }

    public List<String> getDescribeApisId() throws ClientException {
        List<String> apiIds = new ArrayList<>();

        DescribeApisResponse describeApisResponse = this.describeApis(1, 30);

        int totalCount = describeApisResponse.getTotalCount();
        if (totalCount > 0){
            int loopNum = (int)Math.ceil(totalCount / 30.0);

            for (int i=1; i<=loopNum; i++){
                DescribeApisResponse describeApisResponseLoop  = this.describeApis(i, 30);
                List<DescribeApisResponse.ApiSummary> apiSummarys = describeApisResponseLoop.getApiSummarys();
                if (!apiSummarys.isEmpty()){
                    for (DescribeApisResponse.ApiSummary apiSummary : apiSummarys){
                        apiIds.add(apiSummary.getApiId());
                    }
                }
            }


        }

        return apiIds;
    }

    /**
     * TODO 通过appId查询app下的所有api信息
     * @param appId 应用id
     * @return
     * @throws ClientException
     */
    public DescribeApisByAppResponse describeApiByApp(Long appId) throws ClientException {
        DescribeApisByAppRequest request = new DescribeApisByAppRequest();
        request.setAppId(appId);

        return client.getAcsResponse(request);
    }

    /**
     * TODO 部署发布api
     * @param apiId API编号
     * @param stageName 运行环境名称，取值:1.RELEASE(线上) 2.PRE(预发) 3.TEST(测试)
     * @param description 本次发布备注说明
     * @return DeployApiResponse
     * @throws ClientException
     */
    public DeployApiResponse deployApi(String apiId,String stageName,String description) throws ClientException {
        DeployApiRequest request = new DeployApiRequest();
        request.setGroupId(groupId);
        request.setApiId(apiId);
        request.setStageName(stageName);
        request.setDescription(description);

        return client.getAcsResponse(request);
    }

    /**
     * TODO 修改api
     * @param apiName API的名称，组内不允许重复
     * @param visibility API是否公开，目前可以取值：1.PUBLIC(公开) 2.PRIVATE(不公开)
     * @param requestConfig Consumer向网关发送API请求的相关配置项
     * @param serviceConfig 网关向后端服务发送API请求的相关配置项
     * @param resultType 后端服务返回应答的格式，目前可以设置为：JSON、TEXT、BINARY、XML、HTML
     * @param resultSample 后端服务返回应答的示例
     * @param apiId  API编号
     * @return
     * @throws ClientException
     */
    public ModifyApiResponse modifyApi(String apiName,String visibility,String requestConfig,
                                       String serviceConfig,String resultType,
                                       String resultSample,String apiId) throws ClientException {
        ModifyApiRequest request = new ModifyApiRequest();
        request.setApiId(apiId);
        request.setApiName(apiName);
        request.setVisibility(visibility);
        request.setRequestConfig(requestConfig);
        request.setServiceConfig(serviceConfig);
        request.setResultType(resultType);
        request.setResultSample(resultSample);
        request.setGroupId(groupId);

        return client.getAcsResponse(request);

    }

    /**
     * TODO 废除Api
     * @param apiId  API编号
     * @param stageName 运行环境名称，取值:1.RELEASE(线上) 2.PRE(预发) 3.TEST(测试)
     * @return
     * @throws ClientException
     */
    public AbolishApiResponse abolishApi(String apiId, String stageName) throws ClientException {
        AbolishApiRequest request = new AbolishApiRequest();
        request.setApiId(apiId);
        request.setGroupId(groupId);
        request.setStageName(stageName);

        return client.getAcsResponse(request);

    }

    /**
     * TODO 删除api
     * @param apiId
     * @return
     * @throws ClientException
     */
    public DeleteApiResponse deleteApi(String apiId) throws ClientException {
        DeleteApiRequest request = new DeleteApiRequest();
        request.setApiId(apiId);
        request.setGroupId(groupId);

        return client.getAcsResponse(request);
    }

    /**
     * TODO 通过swagger导入api
     * @param dataFormat swagger文本格式：json yaml
     * @param data swagger文本内容
     * @return
     * @throws ClientException
     */
    public ImportSwaggerResponse importSwagger(String dataFormat,String data,boolean bOverwrite) throws ClientException {
        ImportSwaggerRequest request = new ImportSwaggerRequest();
        request.setGroupId(groupId);
        request.setDataFormat(dataFormat);
        request.setData(data);
        request.setbOverwrite(bOverwrite);

        return client.getAcsResponse(request);
    }

    /**
     * TODO 创建Api
     * @param apiName API的名称，组内不允许重复
     * @param visibility API是否公开，目前可以取值：1.PUBLIC(公开) 2.PRIVATE(不公开)
     * @param requestConfig Consumer向网关发送API请求的相关配置项
     * @param serviceConfig 网关向后端服务发送API请求的相关配置项
     * @param resultType 后端服务返回应答的格式，目前可以设置为：JSON、TEXT、BINARY、XML、HTML
     * @param resultSample 后端服务返回应答的示例
     * @param authType API安全认证类型 可取值1.APP(只允许已授权的APP调用) 2.ANONYMOUS(允许匿名调用) 3.OPENID(是一套基于 OAuth 2.0 协议的轻量级规范) 4.APPOPENID(同时进行OpenID Connect和阿里云APP认证)
     * @return CreateApiResponse
     * @throws ClientException 异常
     */
    public CreateApiResponse createApi(String apiName,String visibility,String requestConfig,
                                       String serviceConfig,String resultType,
                                       String resultSample,String authType) throws ClientException {

        CreateApiRequest request = new CreateApiRequest();
        request.setGroupId(groupId);
        request.setVisibility(visibility);
        request.setRequestConfig(requestConfig);
        request.setApiName(apiName);
        request.setServiceConfig(serviceConfig);
        request.setResultType(resultType);
        request.setResultSample(resultSample);
        request.setAuthType(authType);

        return client.getAcsResponse(request);
    }

    /**
     * TODO 提供request实体来创建api
     * @param request CreateApiRequest
     * @return
     * @throws ClientException
     */
    public CreateApiResponse createApiByReq(CreateApiRequest request, DescribeApiResponse.RequestConfig requestConfig,
                                            DescribeApiResponse.ServiceConfig serviceConfig,
                                            List<DescribeApiResponse.RequestParameter> requestParameters,
                                            List<DescribeApiResponse.ServiceParameter> serviceParameters,
                                            List<DescribeApiResponse.ServiceParameterMap> serviceParameterMaps
    ) throws ClientException {

        requestConfig.setRequestProtocol(protocol);
        requestConfig.setBodyFormat(bodyFormat);
        serviceConfig.setServiceProtocol(protocol);

        serviceConfig.setServiceTimeout(serviceTimeout);
        serviceConfig.setServiceAddress(serviceAddress);


        request.setGroupId(groupId);
        request.setVisibility(visibility);
        request.setAuthType(authType);
        request.setResultType(resultType);
        request.setResultSample("200");
        request.setRequestConfig(requestConfig);
        request.setServiceConfig(serviceConfig);

        request.setRequestParameters(requestParameters);
        request.setServiceParameters(serviceParameters);
        request.setServiceParametersMap(serviceParameterMaps);


        return client.getAcsResponse(request);
    }

    /**
     * TODO setVpcAccess
     * @param name
     * @param vpcId
     * @param instanceId
     * @param port
     * @return
     * @throws ClientException
     */
    public SetVpcAccessResponse setVpcAccess(String name,String vpcId,String instanceId,Integer port) throws ClientException {
        SetVpcAccessRequest request = new SetVpcAccessRequest();
        request.setName(name);
        request.setVpcId(vpcId);
        request.setInstanceId(instanceId);
        request.setPort(port);

        return client.getAcsResponse(request);
    }


    public SetApisAuthoritiesResponse setAppsAuthoritiesResponse(Long appId, String apiIds) throws ClientException {
        SetApisAuthoritiesRequest setApisAuthoritiesRequest = new SetApisAuthoritiesRequest();
        setApisAuthoritiesRequest.setApiIds(apiIds);
        setApisAuthoritiesRequest.setAppId(appId);
        setApisAuthoritiesRequest.setGroupId(groupId);
        setApisAuthoritiesRequest.setStageName("RELEASE");


        return client.getAcsResponse(setApisAuthoritiesRequest);
    }


}
