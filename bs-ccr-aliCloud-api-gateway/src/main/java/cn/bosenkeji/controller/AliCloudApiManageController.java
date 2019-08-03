package cn.bosenkeji.controller;

import cn.bosenkeji.util.AliCloudApiManageUtil;
import cn.bosenkeji.util.Result;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.cloudapi.model.v20160714.*;
import com.aliyuncs.exceptions.ClientException;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.HttpMethod;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.Parameter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author CAJR
 * @create 2019/7/30 10:22
 */
@RestController
@Api(tags = "阿里云api管理接口")
public class AliCloudApiManageController {

    @Resource
    AliCloudApiManageUtil aliCloudApiManageUtil;

    @Resource
    Docket docket;

    @Resource
    DocumentationCache documentationCache;

    @Resource
    ServiceModelToSwagger2Mapper mapper;


    @ApiOperation(value = "获取分组api的ID信息",httpMethod = "GET")
    @GetMapping("/get_describe_apis_id")
    public Result getDescribeApisId() throws ClientException {
        List<String> apiIds = aliCloudApiManageUtil.getDescribeApisId();

        return new Result(apiIds);
    }

    @ApiOperation(value = "授权所有API到APP中",httpMethod = "GET")
    @GetMapping("/set_apps_authorities_response")
    public Result setAppsAuthoritiesResponse() throws ClientException {
        List<String> apiIds = aliCloudApiManageUtil.getDescribeApisId();

        List<List<String>> splitApiIdsList = Lists.partition(apiIds, 30);


        if (!splitApiIdsList.isEmpty()){
            for (List<String> splitApiIds : splitApiIdsList){
                aliCloudApiManageUtil.setAppsAuthoritiesResponse((long) 110565671,
                        splitApiIds.stream().collect(Collectors.joining(",")));

            }
        }


        return new Result(apiIds);
    }


    @ApiOperation(value = "导入consumer中全部接口的api",httpMethod = "GET")
    @GetMapping("/import_consumer_api")
    public Result importAllConsumerApi() throws ClientException {
        Documentation documentation = documentationCache.documentationByGroup(docket.getGroupName());

        DescribeApiResponse.RequestConfig requestConfig = new DescribeApiResponse.RequestConfig();
        DescribeApiResponse.ServiceConfig serviceConfig = new DescribeApiResponse.ServiceConfig();

        Swagger swagger = mapper.mapDocumentation(documentation);
        Map<String, Path> map = swagger.getPaths();

        if (!map.isEmpty()) {
            for (Map.Entry<String, Path> entry : map.entrySet()) {
                CreateApiRequest request = new CreateApiRequest();

                request.setDescription(String.valueOf(swagger.getInfo()));

                /*把路径的'{}'替换成'[]'*/
                String path = entry.getKey().replace('{', '[').replace('}', ']');

                requestConfig.setRequestPath(path);
                serviceConfig.setServicePath(path);
                serviceConfig.setServiceVpcEnable(true);
                serviceConfig.setContentTypeCatagory("CUSTOM");
                serviceConfig.setContentTypeValue(MediaType.APPLICATION_JSON_UTF8_VALUE);

                DescribeApiResponse.VpcConfig vpcConfig = new DescribeApiResponse.VpcConfig();
                vpcConfig.setVpcId("vpc-wz9yjesffaen2f2dcvpru");
                vpcConfig.setInstanceId("lb-wz9v115urnvdiz3y63oef");
                vpcConfig.setName("bs-ccr-test");
                serviceConfig.setVpcConfig(vpcConfig);


                Map<HttpMethod,Operation> operationMap = entry.getValue().getOperationMap();
                /*获取路由的HTTPMethod数组*/
                Object [] httMethods = entry.getValue().getOperationMap().keySet().toArray();


                if (!operationMap.isEmpty()) {
                    for (int i=0;i<operationMap.size();i++) {
                        Operation operation = operationMap.get(httMethods[i]);

                        List<DescribeApiResponse.RequestParameter> requestParameters = new ArrayList<>();
                        List<String> requestParametersName = new ArrayList<>();

                        List<DescribeApiResponse.ServiceParameter> serviceParameters = new ArrayList<>();
                        List<DescribeApiResponse.ServiceParameterMap> serviceParameterMaps = new ArrayList<>();

                        System.out.println("operation.getSummary()--->"+operation.getSummary());
                        request.setApiName(operation.getOperationId());
                        request.setDescription(operation.getSummary());


                        requestConfig.setRequestHttpMethod(String.valueOf(httMethods[i]));

                        /*set一个requestMode参数*/
                        requestConfig.setRequestMode("MAPPING");


                        serviceConfig.setServiceHttpMethod(String.valueOf(httMethods[i]));

                        List<Parameter> parameters = operation.getParameters();
                        if (!parameters.isEmpty()){
                            for (Parameter parameter : parameters){

                                if (parameter.getName() != null){
                                    DescribeApiResponse.RequestParameter requestParameter = new DescribeApiResponse.RequestParameter();
                                    DescribeApiResponse.ServiceParameter serviceParameter = new DescribeApiResponse.ServiceParameter();
                                    DescribeApiResponse.ServiceParameterMap serviceParameterMap = new DescribeApiResponse.ServiceParameterMap();
                                    if (parameter.getIn() == "path"){

                                        requestParameter.setLocation("PATH");
                                        serviceParameter.setLocation("PATH");

                                    } else if (parameter.getIn() == "body"){

                                        requestConfig.setPostBodyDescription(parameter.getDescription());
                                        break;

                                    } else {
                                        requestParameter.setLocation("QUERY");
                                        serviceParameter.setLocation("QUERY");

                                    }

                                    if (parameter.getRequired() == true){

                                        requestParameter.setRequired("REQUIRED");
                                    }

                                    requestParameter.setParameterType("String");
                                    serviceParameter.setParameterType("String");

                                    requestParameter.setApiParameterName(parameter.getName());
                                    serviceParameter.setServiceParameterName(parameter.getName());

                                    serviceParameterMap.setRequestParameterName(parameter.getName());
                                    serviceParameterMap.setServiceParameterName(parameter.getName());

                                    requestParameter.setDescription(parameter.getDescription());


                                    if (!requestParametersName.contains(parameter.getName())){
                                        requestParametersName.add(parameter.getName());


                                        requestParameters.add(requestParameter);
                                        serviceParameters.add(serviceParameter);
                                        serviceParameterMaps.add(serviceParameterMap);
                                    }


                                }



                            }
                        }

                        System.out.println("parameters--->"+JSON.toJSONString(parameters));
                        System.out.println("requestParameters--->"+JSON.toJSONString(requestParameters));
                        System.out.println("serviceParameters--->"+JSON.toJSONString(serviceParameters));
                        this.aliCloudApiManageUtil.createApiByReq(request, requestConfig, serviceConfig, requestParameters, serviceParameters, serviceParameterMaps);

                    }

                }

            }
        }
        return new Result();
    }

    @ApiOperation(value = "查询单个api信息",httpMethod = "GET")
    @GetMapping("/describe_api")
    public DescribeApiResponse describeApi(@RequestParam("apiId") String apiId) throws ClientException {
        return this.aliCloudApiManageUtil.describeApi(apiId);
    }

    @ApiOperation(value = "查询api组信息",httpMethod = "GET")
    @GetMapping("/describe_api_group")
    public DescribeApiGroupResponse describeApiGroup() throws ClientException {
        return this.aliCloudApiManageUtil.describeApiGroup();
    }

    @ApiOperation(value = "根据appId查询该app下的所有api的消息",httpMethod = "GET")
    @GetMapping("/describe_api_by_app")
    public DescribeApisByAppResponse describeApiByApp(Long appId) throws ClientException {
        return this.aliCloudApiManageUtil.describeApiByApp(appId);
    }

    /**
     * TODO 部署发布api
     * @param apiId API编号
     * @param stageName 运行环境名称，取值:1.RELEASE(线上) 2.PRE(预发) 3.TEST(测试)
     * @param description 本次发布备注说明
     * @return DeployApiResponse
     * @throws ClientException
     */
    @ApiOperation(value = "发布api",httpMethod = "GET")
    @GetMapping("/deploy_api")
    public DeployApiResponse deployApi(@RequestParam("apiId") String apiId,@RequestParam("stageName") String stageName,
                                       @RequestParam("description") String description) throws ClientException {


        return this.aliCloudApiManageUtil.deployApi(apiId, stageName, description);
    }


    @ApiOperation(value = "发布所有api",httpMethod = "GET")
    @GetMapping("/deploy_all_api")
    public Result deployAllApi() throws ClientException {
        List<String> describeApisId = aliCloudApiManageUtil.getDescribeApisId();

        if (!describeApisId.isEmpty()){
            for (String apiId :describeApisId){
                this.aliCloudApiManageUtil.deployApi(apiId, "RELEASE", "上线");
            }
        }

        return new Result();
    }

    @ApiOperation(value = "导入Swagger",httpMethod = "GET")
    @GetMapping("/import_to_swagger")
    public ImportSwaggerResponse import2Swagger() throws Exception {
        String json_data = "{\n" +
                "    \"swagger\": \"2.0\",\n" +
                "    \"info\": {\n" +
                "        \"description\": \"博森CCR系统API信息\",\n" +
                "        \"version\": \"1.0.0\",\n" +
                "        \"title\": \"博森CCR系统\",\n" +
                "        \"contact\": {\n" +
                "            \"name\": \"YuXueWen\",\n" +
                "            \"url\": \"https://github.com/xiaoemoxiw\",\n" +
                "            \"email\": \"yuxuewen23@qq.com\"\n" +
                "        },\n" +
                "        \"license\": {\n" +
                "            \"name\": \"Apache 2.0\",\n" +
                "            \"url\": \"http://www.apache.org/licenses/LICENSE-2.0\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"host\": \"127.0.0.1:7030\",\n" +
                "    \"basePath\": \"/\",\n" +
                "    \"tags\": [\n" +
                "        {\n" +
                "            \"name\": \"Coin 货币相关接口\",\n" +
                "            \"description\": \"Coin Controller\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"paths\": {\n" +
                "        \"/coin/\": {\n" +
                "            \"put\": {\n" +
                "                \"x-aliyun-apigateway-api-force-nonce-check\": true,\n" +
                "                \"tags\": [\n" +
                "                    \"Coin 货币相关接口\"\n" +
                "                ],\n" +
                "                \"summary\": \"添加单个货币接口\",\n" +
                "                \"operationId\": \"addCoin\",\n" +
                "                \"consumes\": [\n" +
                "                    \"application/json;charset=UTF-8\"\n" +
                "                ],\n" +
                "                \"produces\": [\n" +
                "                    \"application/json;charset=UTF-8\"\n" +
                "                ],\n" +
                "                \"parameters\": [\n" +
                "                    {\n" +
                "                        \"in\": \"body\",\n" +
                "                        \"name\": \"coin\",\n" +
                "                        \"description\": \"币种实体\",\n" +
                "                        \"required\": true,\n" +
                "                        \"schema\": {\n" +
                "                            \"$ref\": \"#/definitions/Coin\"\n" +
                "                        }\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"responses\": {\n" +
                "                    \"200\": {\n" +
                "                        \"description\": \"OK\",\n" +
                "                        \"schema\": {\n" +
                "                            \"$ref\": \"#/definitions/Result\"\n" +
                "                        }\n" +
                "                    }\n" +
                "                },\n" +
                "                \"deprecated\": false,\n" +
                "                \"x-aliyun-apigateway-backend\": {\n" +
                "                    \"method\": \"put\",\n" +
                "                    \"path\": \"/coin/\",\n" +
                "                    \"timeout\": \"10000\",\n" +
                "                    \"type\": \"HTTP-VPC\",\n" +
                "                    \"vpcAccessName\": \"bs-ccr-test\"\n" +
                "                },\n" +
                "                \"x-aliyun-apigateway-paramater-handling\": \"MAPPING\"\n" +
                "            }\n" +
                "        }\n" +
                "    },\n" +
                "    \"definitions\": {\n" +
                "        \"Coin\": {\n" +
                "            \"type\": \"object\",\n" +
                "            \"properties\": {\n" +
                "                \"createdAt\": {\n" +
                "                    \"description\": \"货币创建时间\",\n" +
                "                    \"$ref\": \"#/definitions/Timestamp\"\n" +
                "                },\n" +
                "                \"id\": {\n" +
                "                    \"type\": \"integer\",\n" +
                "                    \"format\": \"int32\",\n" +
                "                    \"description\": \"货币 id\"\n" +
                "                },\n" +
                "                \"name\": {\n" +
                "                    \"type\": \"string\",\n" +
                "                    \"description\": \"货币名\"\n" +
                "                },\n" +
                "                \"status\": {\n" +
                "                    \"type\": \"integer\",\n" +
                "                    \"format\": \"int32\",\n" +
                "                    \"description\": \"货币状态\"\n" +
                "                },\n" +
                "                \"updatedAt\": {\n" +
                "                    \"description\": \"货币更新时间\",\n" +
                "                    \"$ref\": \"#/definitions/Timestamp\"\n" +
                "                }\n" +
                "            },\n" +
                "            \"title\": \"Coin\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"schemes\": [\n" +
                "        \"http\"\n" +
                "    ]\n" +
                "}";


        return this.aliCloudApiManageUtil.importSwagger("json", json_data,true);
    }

    /**
     * TODO 修改api
     * @param apiName
     * @param visibility
     * @param requestConfig
     * @param serviceConfig
     * @param resultType
     * @param resultSample
     * @param apiId
     * @return
     * @throws ClientException
     */
    @ApiOperation(value = "修改api",httpMethod = "GET")
    @GetMapping("/modify_api")
    public ModifyApiResponse modifyApi(@RequestParam("apiName") String apiName,@RequestParam("visibility") String visibility, @RequestParam("requestConfig") String requestConfig,
                                       @RequestParam("serviceConfig") String serviceConfig,@RequestParam("resultType") String resultType,
                                       @RequestParam("resultSample") String resultSample,@RequestParam("apiId") String apiId) throws ClientException {
        return this.aliCloudApiManageUtil.modifyApi(apiName, visibility, requestConfig, serviceConfig, resultType, resultSample, apiId);
    }


    @ApiOperation(value = "废除api",httpMethod = "GET")
    @GetMapping("/abolish_api")
    public AbolishApiResponse abolishApi(@RequestParam("apiId") String apiId, @RequestParam("stageName") String stageName) throws ClientException {
        return this.aliCloudApiManageUtil.abolishApi(apiId, stageName);
    }


    @ApiOperation(value = "下线所有api",httpMethod = "GET")
    @GetMapping("/abolish_all_api")
    public Result abolishAllApi() throws ClientException {
        List<String> describeApisId = aliCloudApiManageUtil.getDescribeApisId();
        if (!describeApisId.isEmpty()){

            //需要改造成多线程操作
            for (String api : describeApisId) {
                aliCloudApiManageUtil.abolishApi(api, "RELEASE");
            }
        }
        return new Result();
    }

    @ApiOperation(value = "删除api",httpMethod = "GET")
    @GetMapping("/delete_api")
    public DeleteApiResponse deleteApi(String apiId) throws ClientException {
        return this.aliCloudApiManageUtil.deleteApi(apiId);
    }

    @ApiOperation(value = "删除所有api",httpMethod = "GET")
    @GetMapping("/delete_all_api")
    public Result deleteAllApi(String apiId) throws ClientException {
        List<String> describeApisId = aliCloudApiManageUtil.getDescribeApisId();
        if (!describeApisId.isEmpty()){

            //需要改造成多线程操作
            for (String api : describeApisId) {
                aliCloudApiManageUtil.deleteApi(api);
            }
        }
        return new Result();
    }

    @ApiOperation(value = "导入swagger到阿里云网关",httpMethod = "GET")
    @GetMapping("/import_swagger")
    public ImportSwaggerResponse importSwagger(@RequestParam("dataFormat") String dataFormat) throws ClientException {
        String data="";
        return this.aliCloudApiManageUtil.importSwagger(dataFormat, data,true);
    }

    @ApiOperation(value = "创建api",httpMethod = "GET")
    @GetMapping("/create_api")
    public CreateApiResponse createApi(@RequestParam("apiName") String apiName,@RequestParam("visibility") String visibility,@RequestParam("requestConfig") String requestConfig,
                                       @RequestParam("serviceConfig") String serviceConfig,@RequestParam("resultType") String resultType,
                                       @RequestParam("resultSample") String resultSample,@RequestParam("authType") String authType) throws ClientException {
        return this.aliCloudApiManageUtil.createApi(apiName,visibility,requestConfig,serviceConfig,resultType,resultSample,authType);
    }

    @ApiOperation(value = "获取已创建的模型",httpMethod = "GET")
    @GetMapping("/describe_models")
    public DescribeModelsResponse describeModels() throws ClientException {

        return this.aliCloudApiManageUtil.describeModels();
    }


}
