package ie.ucd.le_barbz.controller.admin;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.crypto.password.PasswordEncoder;

import ie.ucd.le_barbz.model.Cart;
import ie.ucd.le_barbz.model.Customer;
import ie.ucd.le_barbz.repository.CartRepository;
import ie.ucd.le_barbz.repository.CustomerRepository;
import ie.ucd.le_barbz.service.SessionService;

@Controller
public class AdminSignUpController {

  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private CartRepository cartRepository;
  @Autowired
  private SessionService sessionService;

  @GetMapping("/admin/sign-up")
  public String shopSignUp() {
    return "/admin/sign-up.html";
  }

  @PostMapping("/admin/add")
  public @ResponseBody Customer addAdmin(@RequestParam String firstname, @RequestParam String lastname, @RequestParam String email, @RequestParam String username, 
  @RequestParam String password, @RequestParam String phone, HttpSession session) {
    // Encode the password using the default encoding method suggested by Spring
    final String encoded_password = passwordEncoder.encode(password);
    // Prevent the unencoded password from being used
    password = null;

    if (customerRepository.findByUserName(username).isPresent()) {
      throw new IllegalArgumentException("This username is not available");
    }

    Cart cart = cartRepository.getOne(sessionService.getCartId(session));
    Customer customer = new Customer(firstname, lastname, email, username, encoded_password, phone, cart);
    customerRepository.save(customer);
    cart.setCustomer(customer);
    cartRepository.save(cart);
    return customer;
  }

  
}
