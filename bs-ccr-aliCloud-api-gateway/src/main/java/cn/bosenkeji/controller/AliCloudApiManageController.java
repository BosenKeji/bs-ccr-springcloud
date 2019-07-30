package cn.bosenkeji.controller;

import cn.bosenkeji.util.AliCloudApiManageUtil;
import com.aliyuncs.cloudapi.model.v20160714.*;
import com.aliyuncs.exceptions.ClientException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author CAJR
 * @create 2019/7/30 10:22
 */
@RestController
@Api(tags = "阿里云api管理接口")
public class AliCloudApiManageController {

    @Resource
    AliCloudApiManageUtil aliCloudApiManageUtil;

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



}
