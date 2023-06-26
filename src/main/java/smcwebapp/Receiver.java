package smcwebapp;

import java.util.Map;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

// ...

@Component
public class Receiver {

    private final RestHighLevelClient restClient;
    private final String indexName = "dima-node";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Receiver(RestHighLevelClient restClient) {
        this.restClient = restClient;
    }

    @RabbitListener(queues = MessagingRabbitmqApplication.queueName)
    public void receiveMessage(String payload) {
        System.out.println("Received message: " + payload);
        try {
            // Converti la stringa JSON in una mappa
            Map<String, Object> message = objectMapper.readValue(payload, new TypeReference<Map<String, Object>>() {});
    
            // Indicizza il messaggio
            String json = objectMapper.writeValueAsString(message);
            IndexRequest indexRequest = new IndexRequest(indexName).source(json, XContentType.JSON);
            restClient.index(indexRequest, RequestOptions.DEFAULT);
    
            // Documento indicizzato con successo
            System.out.println("Document indexed successfully");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
}
