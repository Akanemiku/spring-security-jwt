package github.akanemiku.springsecurityjwt.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RouteController {

    @GetMapping("/login")
    public String login() {return "login.html";}

    @GetMapping("/operation")
    public String op(){return "operation.html";}
}
