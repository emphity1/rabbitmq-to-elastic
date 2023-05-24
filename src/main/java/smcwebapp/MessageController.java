package smcwebapp;

// File: MessageController.java

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ModelAndView publishMessage(@RequestParam("message") String message) {
        rabbitTemplate.convertAndSend(MessagingRabbitmqApplication.topicExchangeName, "foo.bar.baz", message);
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("successMessage", "Message published successfully!");
        return modelAndView;
    }
}
