package smcwebapp;

import java.io.IOException;

// File: MessageController.java

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MessageController {

    private final RabbitTemplate rabbitTemplate;

    public MessageController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @PostMapping("/publish")
    public ModelAndView publishMessage(MultipartHttpServletRequest request) {
        MultipartFile file = request.getFile("file");
        try {
            // Read the JSON file content
            String json = new String(file.getBytes());
            
            // Send the JSON message to RabbitMQ
            rabbitTemplate.convertAndSend(MessagingRabbitmqApplication.topicExchangeName, "foo.bar.baz", json);
            
            ModelAndView modelAndView = new ModelAndView("index");
            modelAndView.addObject("successMessage", "JSON file published successfully!");
            return modelAndView;
        } catch (IOException e) {
            ModelAndView modelAndView = new ModelAndView("index");
            modelAndView.addObject("errorMessage", "Error uploading JSON file.");
            return modelAndView;
        }
    }
}
