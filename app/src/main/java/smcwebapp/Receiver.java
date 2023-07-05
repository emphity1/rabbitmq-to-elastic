package smcwebapp;


import org.elasticsearch.client.RestClient;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.util.Collections;

import com.fasterxml.jackson.databind.JsonNode;

// File: Receiver.java



import com.fasterxml.jackson.databind.ObjectMapper;


// ...

@Component
public class Receiver {

    RestClient restClient = RestClient.builder(new HttpHost("localhost", 9201, "http")).build();
    String indexName = "index-dima";
    ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = MessagingRabbitmqApplication.queueName)
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
        try {
            // Parse the JSON
            JsonNode jsonNode = objectMapper.readTree(message);
            // Index the JSON document
            String jsonString = objectMapper.writeValueAsString(jsonNode);
            // Create HttpClient and HttpPost
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpPost httpPost = new HttpPost("http://localhost:9201/" + indexName + "/_doc");
                // Set the JSON entity and content type
                StringEntity requestEntity = new StringEntity(jsonString, ContentType.APPLICATION_JSON);
                httpPost.setEntity(requestEntity);
                // Add the Content-Type header
                httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
                // Execute the request
                CloseableHttpResponse response = httpClient.execute(httpPost);
                // Check the response status
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 201) {
                    // Document indexed successfully
                    System.out.println("Document indexed successfully");
                } else {
                    // Document indexing failed
                    System.out.println("Failed to index document. Status code: " + statusCode);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }






}
