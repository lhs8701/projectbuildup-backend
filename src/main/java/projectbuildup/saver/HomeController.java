package projectbuildup.saver;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
public class HomeController {
    @GetMapping("/api/usage")
    @ApiIgnore
    public String api() {
        return "redirect:/swagger-ui/index.html";
    }
}
