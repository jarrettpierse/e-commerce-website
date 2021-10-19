package ie.ucd.le_barbz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ie.ucd.le_barbz.model.Cart;
import ie.ucd.le_barbz.model.Customer;
import ie.ucd.le_barbz.model.MyUserDetails;
import ie.ucd.le_barbz.model.ShopOrder;
import ie.ucd.le_barbz.repository.CustomerRepository;
import ie.ucd.le_barbz.repository.ShopOrderRepository;

@Controller
public class CheckoutController {
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private ShopOrderRepository shopOrderRepository;

  @GetMapping("/checkout")
  public String checkout(Model model) {
    final Cart cart = customerRepository
        .findByUserName(
            ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())
        .get().getCart();
    model.addAttribute("user",
        customerRepository
            .findByUserName(
                ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())
            .get());
    model.addAttribute("cart", cart.getProducts());
    model.addAttribute("quantity", cart.getProductCounts());
    model.addAttribute("total", cart.getCost());
    return "checkout.html";
  }

  @GetMapping("/place-order")
  public String placeOrder(Model model) {
    final Customer customer = customerRepository
        .findByUserName(
            ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())
        .get();
    final Cart cart = customerRepository
        .findByUserName(
            ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())
        .get().getCart();

    ShopOrder shopOrder = new ShopOrder();

    // create shopOrder
    shopOrder.getOrderFromCart(cart);
    shopOrder.setCustomer(customer);
    customer.getShopOrders().add(shopOrder);

    // empty cart
    cart.empty();

    // update repositories
    shopOrderRepository.save(shopOrder);
    customerRepository.save(customer);

    return "orders.html";
  }
}
