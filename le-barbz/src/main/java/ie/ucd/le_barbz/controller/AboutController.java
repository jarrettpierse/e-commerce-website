package ie.ucd.le_barbz.controller;

import ie.ucd.le_barbz.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AboutController {
    @Autowired
    private SimpleEmailService ses;

    @GetMapping("/about")
    public String about() {
        return "about.html";
    }

    @PostMapping("/about/email")
    public @ResponseBody void contactForm(@RequestParam String name, @RequestParam String email,
            @RequestParam String message) {
        StringBuilder sb = new StringBuilder("Name: " + name + "\nEmail: " + email + "\nMessage: " + message);
        ses.sendEmail("le.barbz.30860@gmail.com", name, sb.toString());
    }
}
