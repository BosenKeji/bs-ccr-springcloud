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

    @Value("${open-search.access-key}")
    private String accessKey;

    @Value("${open-search.secret-key}")
    private String secretKey;

    @Value("${open-search.host}")
    private String host;

    @Value("${open-search.appName}")
    private String appName;

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
