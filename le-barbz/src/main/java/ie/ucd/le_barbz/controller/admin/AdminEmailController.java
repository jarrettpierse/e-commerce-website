package ie.ucd.le_barbz.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ie.ucd.le_barbz.model.Customer;
import ie.ucd.le_barbz.model.ShopOrder;
import ie.ucd.le_barbz.repository.ShopOrderRepository;
import ie.ucd.le_barbz.service.SimpleEmailService;

@Controller
public class AdminEmailController {
    @Autowired
    private SimpleEmailService ses;

    @Autowired
    private ShopOrderRepository shopOrderRepository;
    
    @GetMapping("/admin/send-email")
    public String email() {
        return "admin/send-email.html";
    }

    @PostMapping("/admin/email")
    public @ResponseBody void contactForm(@RequestParam String email, @RequestParam String subject,
            @RequestParam String message) {
        ses.sendEmail(email, subject, message);
    }

    @GetMapping("/admin/email/customer")
    public String contactCustomer(@RequestParam Integer orderId, Model model) {
        ShopOrder shopOrder = shopOrderRepository.findById(orderId).get();
        Customer customer = shopOrder.getCustomer();
        
        model.addAttribute("Order", shopOrder);
        model.addAttribute("Customer", customer);

        return "admin/send-email.html";
    }
}
