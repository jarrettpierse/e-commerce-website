package ie.ucd.le_barbz.controller;

import java.lang.annotation.Repeatable;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ie.ucd.le_barbz.model.Cart;
import ie.ucd.le_barbz.model.Customer;
import ie.ucd.le_barbz.model.MyUserDetails;
import ie.ucd.le_barbz.model.ShopOrder;
import ie.ucd.le_barbz.model.Wishlist;
import ie.ucd.le_barbz.repository.CartRepository;
import ie.ucd.le_barbz.repository.CustomerRepository;
import ie.ucd.le_barbz.repository.ShopOrderRepository;
import ie.ucd.le_barbz.repository.WishlistRepository;
import ie.ucd.le_barbz.service.SessionService;

@Controller
public class AccountController {

  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private SessionService sessionService;
  @Autowired
  private CartRepository cartRepository;
  @Autowired
  private WishlistRepository wishlistRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private ShopOrderRepository shopOrderRepository;
  
  @GetMapping("/account/view-account")
  public String viewAccount(Model model) {
    final Customer customer = customerRepository
        .findByUserName(
            ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())
        .get();
    model.addAttribute("user", customer);
    System.out.println(customer.getUserName());
    return "view-account.html";
  }

  @PostMapping("/account/modify")
  public @ResponseBody void addAccount(@RequestParam String firstname, @RequestParam String lastname, @RequestParam String email, @RequestParam String phone, 
  @RequestParam String address1, @RequestParam String address2, @RequestParam String city, @RequestParam String county, @RequestParam String country,
  @RequestParam String postalCode, HttpSession session) {
    
    final Customer customer = customerRepository
        .findByUserName(
            ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())
        .get();

        customer.setFirstname(firstname);
        customer.setSurname(lastname);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddressLine1(address1);
        customer.setAddressLine2(address2);
        customer.setCity(city);
        customer.setCounty(county);
        customer.setCountry(country);
        customer.setPostalCode(postalCode);
        customerRepository.save(customer);
  }

  @PostMapping("/account/add")
  public @ResponseBody Customer addAccount(@RequestParam String firstname, @RequestParam String lastname, @RequestParam String email, @RequestParam String username, 
  @RequestParam String password, @RequestParam String phone, @RequestParam String address1, @RequestParam String address2,
  @RequestParam String city, @RequestParam String county, @RequestParam String country,
  @RequestParam String postalCode, HttpSession session) {
    System.out.println(session);
    // Encode the password using the default encoding method suggested by Spring
    final String encoded_password = passwordEncoder.encode(password);
    // Prevent the unencoded password from being used
    password = null;

    if (customerRepository.findByUserName(username).isPresent()) {
      throw new IllegalArgumentException("This username is not available");
    }

    Cart cart = cartRepository.getOne(sessionService.getCartId(session));
    Wishlist wishlist = wishlistRepository.getOne(sessionService.getWishlistId(session));
    wishlist = wishlistRepository.save(wishlist);
    Customer customer = new Customer(firstname, lastname, email, username, encoded_password, 
    address1, address2, city, county, country, postalCode, phone, cart, wishlist);
    customer = customerRepository.save(customer);
    cart.setCustomer(customer);
    wishlist.setCustomer(customer);
    cartRepository.save(cart);
    wishlistRepository.save(wishlist);
    return customer;
  }

  @GetMapping("/account/orders")
  public String viewOrders(Model model) {
    final Customer customer = customerRepository
        .findByUserName(
            ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())
        .get();

    model.addAttribute("orders", customer.getShopOrders());
    return "orders.html";
  }

  @GetMapping("/account/order-details/{id}")
  public String viewOrderDetails(@PathVariable int id, Model model) {
    model.addAttribute("order", shopOrderRepository.getOne(id));
    return "order-details.html";
  }

  @PostMapping("/account/order-details/close-order")
  public @ResponseBody void closeState(@RequestParam int id){
    ShopOrder order = shopOrderRepository.getOne(id);
    order.closeOrder();
    shopOrderRepository.save(order);
  }

  @GetMapping("/account/wishlist")
  public String viewWishlist(Model model, HttpSession session) {
    model.addAttribute("wishlist", wishlistRepository.getOne(sessionService.getWishlistId(session)));
    return "wishlist.html";
  }
}
