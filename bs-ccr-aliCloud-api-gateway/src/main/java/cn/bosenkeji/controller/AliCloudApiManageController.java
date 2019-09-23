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
import java.util.concurrent.*;
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

        return new Result<>(apiIds);
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
                            //begin for
                            settingParameter(parameters,requestConfig,requestParametersName,serviceParameters,serviceParameterMaps,requestParameters);
                            //end for
//                            settingParameter2(serviceParameters,serviceParameterMaps,requestParameters);
                        }

                        System.out.println("parameters--->"+JSON.toJSONString(parameters));
                        System.out.println("requestParameters--->"+JSON.toJSONString(requestParameters));
                        System.out.println("serviceParameters--->"+JSON.toJSONString(serviceParameters));
                        System.out.println("serviceParameterMaps--->"+JSON.toJSONString(serviceParameterMaps));
                        this.aliCloudApiManageUtil.createApiByReq(request, requestConfig, serviceConfig, requestParameters, serviceParameters, serviceParameterMaps);
                    }

                }

            }
        }

        return new Result();
    }

    /**
     * TODO 创建api设置api参数
     * @param parameters
     * @param requestConfig
     * @param requestParametersName
     * @param serviceParameters
     * @param serviceParameterMaps
     * @param requestParameters
     */
    private void settingParameter(List<Parameter> parameters,DescribeApiResponse.RequestConfig requestConfig,
                                  List<String> requestParametersName,List<DescribeApiResponse.ServiceParameter> serviceParameters,
                                  List<DescribeApiResponse.ServiceParameterMap> serviceParameterMaps,
                                  List<DescribeApiResponse.RequestParameter> requestParameters){
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
                } else if (parameter.getIn() == "header"){

                    requestParameter.setLocation("HEAD");
                    serviceParameter.setLocation("HEAD");
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

    /**
     * TODO 创建api设置api参数2
     * @param serviceParameters
     * @param serviceParameterMaps
     * @param requestParameters
     */
    private void settingParameter2(List<DescribeApiResponse.ServiceParameter> serviceParameters,
                                   List<DescribeApiResponse.ServiceParameterMap> serviceParameterMaps,
                                   List<DescribeApiResponse.RequestParameter> requestParameters){

        DescribeApiResponse.RequestParameter requestParameter = new DescribeApiResponse.RequestParameter();
        DescribeApiResponse.ServiceParameter serviceParameter = new DescribeApiResponse.ServiceParameter();
        DescribeApiResponse.ServiceParameterMap serviceParameterMap = new DescribeApiResponse.ServiceParameterMap();

        requestParameter.setLocation("HEAD");
        serviceParameter.setLocation("HEAD");

        requestParameter.setParameterType("String");
        serviceParameter.setParameterType("String");

        requestParameter.setApiParameterName("Authorization");
        serviceParameter.setServiceParameterName("Authorization");

        requestParameter.setRequired("REQUIRED");

        serviceParameterMap.setRequestParameterName("Authorization");
        serviceParameterMap.setServiceParameterName("Authorization");


        requestParameters.add(requestParameter);
        serviceParameters.add(serviceParameter);
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




    @ApiOperation(value = "发布所有api",httpMethod = "GET")
    @GetMapping("/deploy_all_api")
    public Result deployAllApi() throws ClientException {
        long start = System.currentTimeMillis();

        //建议10个线程以下
        int nThreads =4;

        CountDownLatch countDownLatch = new CountDownLatch(nThreads);

        List<String> describeApisId = aliCloudApiManageUtil.getDescribeApisId();
        int size = describeApisId.size();
        ConcurrentLinkedQueue<String> describeApisIdQueue = new ConcurrentLinkedQueue<>(describeApisId);

        if (!describeApisIdQueue.isEmpty()){

            //需要改造成多线程操作
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(nThreads,nThreads+5,60, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());
            for (int i=0;i<nThreads;i++) {
//                final List<String> suList ;
//                if (size-size/nThreads*(i+1) < size/nThreads){
//                    suList= describeApisId.subList(size/nThreads*i,size);
//                }else {
//                    suList = describeApisId.subList(size/nThreads*i,size/nThreads*(i+1));
//                }
                threadPoolExecutor.execute(()->{
                    try {
                        while (!describeApisIdQueue.isEmpty()){
                            String apiId = describeApisIdQueue.poll();
                            this.aliCloudApiManageUtil.deployApi(apiId, "RELEASE", "上线");
                            System.out.println(Thread.currentThread().getName()+"-->"+apiId);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        countDownLatch.countDown();
                    }
                });
            }

            try {
                countDownLatch.await();
                long end = System.currentTimeMillis();
                System.out.println("消耗的时间为：");
                System.out.println(end-start);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        return new Result();
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


    @ApiOperation(value = "下线所有api",httpMethod = "GET")
    @GetMapping("/abolish_all_api")
    public Result abolishAllApi() throws ClientException {
        long start = System.currentTimeMillis();

        List<String> describeApisId = aliCloudApiManageUtil.getDescribeApisId();
        if (!describeApisId.isEmpty()){

            //需要改造成多线程操作
            for (String api : describeApisId) {
                aliCloudApiManageUtil.abolishApi(api, "RELEASE");
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("消耗的时间为：");
        System.out.println(end-start);
        return new Result();
    }


    @ApiOperation(value = "删除所有api",httpMethod = "GET")
    @GetMapping("/delete_all_api")
    public Result deleteAllApi() throws ClientException {
        long start = System.currentTimeMillis();

        //建议10个线程以下
        int nThreads =4;

        CountDownLatch countDownLatch = new CountDownLatch(nThreads);

        List<String> describeApisId = aliCloudApiManageUtil.getDescribeApisId();
        long end = System.currentTimeMillis();
        System.out.println("消耗的时间为：");
        System.out.println(end-start);

        int size = describeApisId.size();
        ConcurrentLinkedQueue<String> describeApisIdQueue = new ConcurrentLinkedQueue<>(describeApisId);

        if (!describeApisIdQueue.isEmpty()){

            //需要改造成多线程操作
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(nThreads,nThreads+5,60, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());
            for (int i=0;i<nThreads;i++) {
                threadPoolExecutor.execute(()->{
                    try {
                        while(!describeApisIdQueue.isEmpty()){
                            String id = describeApisIdQueue.poll();
                            aliCloudApiManageUtil.deleteApi(id);
                            System.out.println(Thread.currentThread().getName()+"-->"+id);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        countDownLatch.countDown();
                    }
                });
            }
//            //需要改造成多线程操作
//            for (String api : describeApisId) {
//                aliCloudApiManageUtil.deleteApi(api);
//            }
        }

        long end1 = System.currentTimeMillis();
        System.out.println("消耗的时间为：");
        System.out.println(end1-start);
        return new Result();
    }

    @ApiOperation(value = "获取已创建的模型",httpMethod = "GET")
    @GetMapping("/describe_models")
    public DescribeModelsResponse describeModels() throws ClientException {

        return this.aliCloudApiManageUtil.describeModels();
    }

}