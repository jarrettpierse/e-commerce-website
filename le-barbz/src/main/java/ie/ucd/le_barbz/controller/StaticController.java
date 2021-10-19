package ie.ucd.le_barbz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class StaticController {
  

  @GetMapping("/terms")
  public String terms() {
    return "terms.html";
  }

  @GetMapping("/error")
  public String error() {
    return "error.html";
  }

  @GetMapping("/order-success")
  public String orderSuccess() {
    return "order-success.html";
  }

  @GetMapping("/covid-19")
  public String covid() {
    return "covid-19.html";
  }
}
