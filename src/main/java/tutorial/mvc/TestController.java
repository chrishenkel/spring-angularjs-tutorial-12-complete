package tutorial.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Chris on 6/5/14.
 */
@Controller
public class TestController {
    @RequestMapping("/test")
    public String test()
    {
        return "view";
    }
}
