package smcwebapp;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {


  

    @Bean
    public RestHighLevelClient elasticsearchCustomClient() {

        return new RestHighLevelClient(
                RestClient.builder(new HttpHost("node1", 9200, "http"))
        );
    }
}
