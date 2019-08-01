package cn.bosenkeji.controller;

import cn.bosenkeji.util.AliCloudApiManageUtil;
import cn.bosenkeji.util.Result;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.cloudapi.model.v20160714.*;
import com.aliyuncs.exceptions.ClientException;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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


    @ApiOperation(value = "导入consumer中全部接口的api",httpMethod = "GET")
    @GetMapping("/import_consumer_api")
    @SuppressWarnings("all")
    public Result importAllConsumerApi() throws ClientException {
        Documentation documentation = documentationCache.documentationByGroup(docket.getGroupName());

        System.out.println(docket.getGroupName());

        DescribeApiResponse.RequestConfig requestConfig = new DescribeApiResponse.RequestConfig();
        DescribeApiResponse.ServiceConfig serviceConfig = new DescribeApiResponse.ServiceConfig();

        Swagger swagger = mapper.mapDocumentation(documentation);
        Map<String, Path> map = swagger.getPaths();
        System.out.println(swagger);
        System.out.println(swagger.getInfo().getDescription());
        if (!map.isEmpty()) {
            for (Map.Entry<String, Path> entry : map.entrySet()) {
                CreateApiRequest request = new CreateApiRequest();

                request.setDescription(String.valueOf(swagger.getInfo()));
                System.out.println("getKey---->" + entry.getKey());

                /*把路径的'{}'替换成'[]'*/
                String path = entry.getKey().replace('{', '[').replace('}', ']');
                System.out.println(path);

                requestConfig.setRequestPath(path);
                serviceConfig.setServicePath(path);

                List<Operation> operations = entry.getValue().getOperations();

                if (!operations.isEmpty()) {
                    for (Operation operation : operations) {
                        System.out.println(operation.getDescription());
                        request.setApiName(operation.getOperationId());
                        request.setDescription(operation.getSummary());

                        /*获取路由的HTTPMethod名*/
                        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(operation.getVendorExtensions().get("x-aliyun-apigateway-backend"));
                        System.out.println(jsonObject.get("method"));

                        System.out.println(JSON.toJSONString(entry.getValue().getOperationMap().keySet()));
                        entry.getValue().getOperationMap();

                        requestConfig.setRequestHttpMethod((String) jsonObject.get("method"));

                        /*set一个requestMode参数*/
                        requestConfig.setRequestMode((String) operation.getVendorExtensions().get("x-aliyun-apigateway-paramater-handling"));

                        serviceConfig.setServiceHttpMethod((String) jsonObject.get("method"));

                    }
                    System.out.println(request.toString());
                }

                this.aliCloudApiManageUtil.createApiByReq(request, requestConfig, serviceConfig);
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
                "                    },\n" +
                "                    \"201\": {\n" +
                "                        \"description\": \"添加或者修改成功\"\n" +
                "                    },\n" +
                "                    \"400\": {\n" +
                "                        \"description\": \"错误请求\"\n" +
                "                    },\n" +
                "                    \"401\": {\n" +
                "                        \"description\": \"未授权\"\n" +
                "                    },\n" +
                "                    \"403\": {\n" +
                "                        \"description\": \"拒绝请求\"\n" +
                "                    },\n" +
                "                    \"404\": {\n" +
                "                        \"description\": \"未找到相关的请求\"\n" +
                "                    },\n" +
                "                    \"500\": {\n" +
                "                        \"description\": \"服务器内部错误\"\n" +
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
                "        },\n" +
                "        \"PageInfo\": {\n" +
                "            \"type\": \"object\",\n" +
                "            \"properties\": {\n" +
                "                \"endRow\": {\n" +
                "                    \"type\": \"integer\",\n" +
                "                    \"format\": \"int32\"\n" +
                "                },\n" +
                "                \"hasNextPage\": {\n" +
                "                    \"type\": \"boolean\"\n" +
                "                },\n" +
                "                \"hasPreviousPage\": {\n" +
                "                    \"type\": \"boolean\"\n" +
                "                },\n" +
                "                \"isFirstPage\": {\n" +
                "                    \"type\": \"boolean\"\n" +
                "                },\n" +
                "                \"isLastPage\": {\n" +
                "                    \"type\": \"boolean\"\n" +
                "                },\n" +
                "                \"list\": {\n" +
                "                    \"type\": \"array\",\n" +
                "                    \"items\": {\n" +
                "                        \"type\": \"object\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"navigateFirstPage\": {\n" +
                "                    \"type\": \"integer\",\n" +
                "                    \"format\": \"int32\"\n" +
                "                },\n" +
                "                \"navigateLastPage\": {\n" +
                "                    \"type\": \"integer\",\n" +
                "                    \"format\": \"int32\"\n" +
                "                },\n" +
                "                \"navigatePages\": {\n" +
                "                    \"type\": \"integer\",\n" +
                "                    \"format\": \"int32\"\n" +
                "                },\n" +
                "                \"navigatepageNums\": {\n" +
                "                    \"type\": \"array\",\n" +
                "                    \"items\": {\n" +
                "                        \"type\": \"integer\",\n" +
                "                        \"format\": \"int32\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"nextPage\": {\n" +
                "                    \"type\": \"integer\",\n" +
                "                    \"format\": \"int32\"\n" +
                "                },\n" +
                "                \"pageNum\": {\n" +
                "                    \"type\": \"integer\",\n" +
                "                    \"format\": \"int32\"\n" +
                "                },\n" +
                "                \"pageSize\": {\n" +
                "                    \"type\": \"integer\",\n" +
                "                    \"format\": \"int32\"\n" +
                "                },\n" +
                "                \"pages\": {\n" +
                "                    \"type\": \"integer\",\n" +
                "                    \"format\": \"int32\"\n" +
                "                },\n" +
                "                \"prePage\": {\n" +
                "                    \"type\": \"integer\",\n" +
                "                    \"format\": \"int32\"\n" +
                "                },\n" +
                "                \"size\": {\n" +
                "                    \"type\": \"integer\",\n" +
                "                    \"format\": \"int32\"\n" +
                "                },\n" +
                "                \"startRow\": {\n" +
                "                    \"type\": \"integer\",\n" +
                "                    \"format\": \"int32\"\n" +
                "                },\n" +
                "                \"total\": {\n" +
                "                    \"type\": \"integer\",\n" +
                "                    \"format\": \"int64\"\n" +
                "                }\n" +
                "            },\n" +
                "            \"title\": \"PageInfo\"\n" +
                "        },\n" +
                "        \"Result\": {\n" +
                "            \"type\": \"object\",\n" +
                "            \"properties\": {\n" +
                "                \"data\": {\n" +
                "                    \"type\": \"object\"\n" +
                "                },\n" +
                "                \"msg\": {\n" +
                "                    \"type\": \"string\"\n" +
                "                }\n" +
                "            },\n" +
                "            \"title\": \"Result\"\n" +
                "        },\n" +
                "        \"Timestamp\": {\n" +
                "            \"type\": \"object\",\n" +
                "            \"properties\": {\n" +
                "                \"date\": {\n" +
                "                    \"type\": \"integer\",\n" +
                "                    \"format\": \"int32\"\n" +
                "                },\n" +
                "                \"day\": {\n" +
                "                    \"type\": \"integer\",\n" +
                "                    \"format\": \"int32\"\n" +
                "                },\n" +
                "                \"hours\": {\n" +
                "                    \"type\": \"integer\",\n" +
                "                    \"format\": \"int32\"\n" +
                "                },\n" +
                "                \"minutes\": {\n" +
                "                    \"type\": \"integer\",\n" +
                "                    \"format\": \"int32\"\n" +
                "                },\n" +
                "                \"month\": {\n" +
                "                    \"type\": \"integer\",\n" +
                "                    \"format\": \"int32\"\n" +
                "                },\n" +
                "                \"nanos\": {\n" +
                "                    \"type\": \"integer\",\n" +
                "                    \"format\": \"int32\"\n" +
                "                },\n" +
                "                \"seconds\": {\n" +
                "                    \"type\": \"integer\",\n" +
                "                    \"format\": \"int32\"\n" +
                "                },\n" +
                "                \"time\": {\n" +
                "                    \"type\": \"integer\",\n" +
                "                    \"format\": \"int64\"\n" +
                "                },\n" +
                "                \"timezoneOffset\": {\n" +
                "                    \"type\": \"integer\",\n" +
                "                    \"format\": \"int32\"\n" +
                "                },\n" +
                "                \"year\": {\n" +
                "                    \"type\": \"integer\",\n" +
                "                    \"format\": \"int32\"\n" +
                "                }\n" +
                "            },\n" +
                "            \"title\": \"Timestamp\"\n" +
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

    @ApiOperation(value = "删除api",httpMethod = "GET")
    @GetMapping("/delete_api")
    public DeleteApiResponse deleteApi(String apiId) throws ClientException {
        return this.aliCloudApiManageUtil.deleteApi(apiId);
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


}
