package smcwebapp;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {


    //String node1IP = System.getenv("NODE1_PORT_9201_TCP_ADDR");

    //String elasticsearchHost = "http://" + node1IP;

    //@Value("${ELASTICSEARCH_PORT}")
    //private int elasticsearchPort;

    @Bean
    public RestHighLevelClient elasticsearchCustomClient() {

        return new RestHighLevelClient(
                RestClient.builder(new HttpHost("node1", 9200, "http"))
        );
    }
}
