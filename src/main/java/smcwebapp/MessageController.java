package smcwebapp;

import java.util.Map;

// File: MessageController.java

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
    public ModelAndView publishMessage(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {

                String json = new String(file.getBytes());

                rabbitTemplate.convertAndSend(MessagingRabbitmqApplication.topicExchangeName, "foo.bar.baz", json);
                
                ModelAndView modelAndView = new ModelAndView("index");
                modelAndView.addObject("successMessage", "File uploaded successfully!");
                return modelAndView;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("errorMessage", "Failed to upload file");
        return modelAndView;
    }
}
