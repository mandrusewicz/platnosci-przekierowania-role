package com.zespol11.programowanienzespolowe.userRegistration.login;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class CustomSuccessHandler {
    @RequestMapping("/")
    public String defaultAfterLogin() {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean hasUserRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("USER"));
        boolean hasCookRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("COOK"));

      if(hasUserRole) return "redirect:/api/v1/restaurant/protected";

        if(hasCookRole) return "redirect:/api/v1/restaurant/kucharz";
        
        return "redirect:/api/v1/restaurant/hello";
    }
}