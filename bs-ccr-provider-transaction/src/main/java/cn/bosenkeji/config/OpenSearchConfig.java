package cn.bosenkeji.config;

import com.aliyun.opensearch.DocumentClient;
import com.aliyun.opensearch.OpenSearchClient;
import com.aliyun.opensearch.sdk.dependencies.com.google.common.collect.Lists;
import com.aliyun.opensearch.sdk.generated.OpenSearch;
import com.aliyun.opensearch.sdk.generated.search.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * @author CAJR
 * @date 2019/11/5 5:46 下午
 */
@Configuration
public class OpenSearchConfig {

    //@Value("${open-search.access-key}")
    //@Value("${spring.cloud.nacos.config.access-key}")
    private String accessKey="LTAIEBWhTbhsWByO";

    //@Value("${open-search.secret-key}")
    //@Value("${spring.cloud.nacos.config.secret-key}")
    private String secretKey="FXqHcqjJMPftFuMjH1HOZeGpAGZ7Ko";

    //@Value("${open-search.host}")
    private String host="http://opensearch-cn-qingdao.aliyuncs.com";

    //@Value("${open-search.appName}")
    private String appName="";

    @Bean(name = "openSearchClient")
    public OpenSearchClient openSearchClient(){
        OpenSearch openSearch = new OpenSearch(accessKey,secretKey,host);
        return new OpenSearchClient(openSearch);
    }

    @Bean
    @DependsOn("openSearchClient")
    public DocumentClient documentClient(OpenSearchClient openSearchClient){
        return new DocumentClient(openSearchClient);
    }
}
