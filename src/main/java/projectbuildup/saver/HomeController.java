package projectbuildup.saver;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/api/usage")
    public String redirect(){
        return "redirect:/swagger-ui.html";
    }
}
