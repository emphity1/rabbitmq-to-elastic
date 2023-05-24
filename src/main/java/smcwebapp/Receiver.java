package smcwebapp;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;

import java.util.Collections;



// File: Receiver.java



import com.fasterxml.jackson.databind.ObjectMapper;


// ...

@Component
public class Receiver {

    RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200, "http")).build();
    String indexName = "index-dima";
    ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = MessagingRabbitmqApplication.queueName)
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
        try {
            // Index the message
            String json = objectMapper.writeValueAsString(Collections.singletonMap("message", message));

            Request request = new Request("POST", "/" + indexName + "/_doc");
            request.setJsonEntity(json);

            Response response = restClient.performRequest(request);

            // Check the response status
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 201) {
                // Document indexed successfully
                System.out.println("Document indexed successfully");
            } else {
                // Document indexing failed
                System.out.println("Failed to index document. Status code: " + statusCode);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
