package ie.ucd.le_barbz.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ie.ucd.le_barbz.model.Cart;
import ie.ucd.le_barbz.model.Product;
import ie.ucd.le_barbz.repository.CartRepository;
import ie.ucd.le_barbz.repository.ProductRepository;
import ie.ucd.le_barbz.service.SessionService;

@Controller
public class CartController {

  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private CartRepository cartRepository;
  @Autowired
  private SessionService sessionService;


  @GetMapping("/cart")
  public String cart(Model model, HttpSession session) {
    final int cart_id = sessionService.getCartId(session);
    model.addAttribute("basket", cartRepository.getOne(cart_id));
    return "cart.html";
  }

  @PostMapping("/cart/changeQuantity")
  public @ResponseBody void changeQuantity(@RequestParam int id, @RequestParam int quantity, HttpSession session) {
    final int cart_id = sessionService.getCartId(session);
    final Cart cart = cartRepository.getOne(cart_id);
    final Product product = productRepository.findById(id).get();
    cart.quantityChange(product, quantity);
    cartRepository.save(cart);
  }

  @PostMapping("/cart/removeProduct")
  public @ResponseBody void removeProduct(@RequestParam int id, HttpSession session) {
    final int cart_id = sessionService.getCartId(session);
    final Cart cart = cartRepository.getOne(cart_id);
    final Product product = productRepository.findById(id).get();
    cart.remove(product);
    cartRepository.save(cart);
  }

  @GetMapping("/fragments/cartSummary")
  public String cartSummary(Model model, HttpSession session){
      final int cart_id = sessionService.getCartId(session);
      model.addAttribute("basket", cartRepository.getOne(cart_id));
      return "fragments/cartSummary.html";
  }

}
