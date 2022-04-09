package com.zespol11.programowanienzespolowe.userRegistration.login;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/restaurant")
public class LoginController {
    @RequestMapping("/protected")
    public String protectedResource() {
        return "The Protected Resource";
    }

    @RequestMapping("/public")
    public String publicResource() {
        return "The Public Resource";
    }

    @RequestMapping("/kucharz")
    public String kucharz() {
        return "kucharz kucharzowski";
    }
    @RequestMapping("/hello")
    public String hello_page() {
        return "hello";
    }
}
