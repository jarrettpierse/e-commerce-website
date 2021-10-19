package ie.ucd.le_barbz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignUpController {
  @GetMapping("/sign-up")
  public String customerSignUp() {
    return "/sign-up.html";
  }
}
