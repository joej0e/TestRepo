package springboot.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BootController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}
