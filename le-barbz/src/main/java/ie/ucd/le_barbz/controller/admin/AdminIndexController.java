package ie.ucd.le_barbz.controller.admin;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.Case;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ie.ucd.le_barbz.model.ShopOrder;
import ie.ucd.le_barbz.repository.ProductRepository;
import ie.ucd.le_barbz.repository.ShopOrderRepository;

@Controller
public class AdminIndexController {

  @Autowired
  private ShopOrderRepository shopOrderRepository;

  @Autowired private ProductRepository productRepository;
  @GetMapping("/admin")
  public String index() {
    return "admin/index.html";
  }

  @GetMapping("/admin/edit-product/{id}")
  public String editProduct(@PathVariable Integer id, Model model) {
    model.addAttribute("product", productRepository.findById(id).get());
    return "admin/edit-product.html";
  }

  @PostMapping("/admin/edit-order/change-state")
  public @ResponseBody void editState(@RequestParam Integer id, @RequestParam String state) {
    ShopOrder order = shopOrderRepository.getOne(id);

    switch (state) {
      case "NEW" :
      order.openOrder();
      shopOrderRepository.save(order);
      break;
      case "SHIPPED" :
      order.inTransitOrder();
      shopOrderRepository.save(order);
      break;
      case "DELIVERED" :
      order.deliveredOrder();
      shopOrderRepository.save(order);
      break;
      case "CANCELLED" :
      order.closeOrder();
      shopOrderRepository.save(order);
      break;
    }
  }

  @GetMapping("/admin/edit-order/{id}")
  public String editOrder(@PathVariable Integer id, Model model) {
    model.addAttribute("order", shopOrderRepository.findById(id).get());
    return "admin/edit-order.html";
  }

  @GetMapping("/admin/view-inventory")
  public String viewInventory(Model model) {
    model.addAttribute("products", productRepository.findAll());
    return "admin/view-inventory.html";
  }

  @GetMapping("/admin/order-history")
  public String viewOrders(@RequestParam String status, Model model) {
    List<ShopOrder> list = shopOrderRepository.findAll();
    LinkedList<ShopOrder> listOfOrders = new LinkedList<ShopOrder>();

    for(ShopOrder order : list){
      if(order.sameState(status)){
        listOfOrders.add(order);
      }
    }

    model.addAttribute("orders", listOfOrders);
    return "admin/fragments/order-history.html";
  }
}
