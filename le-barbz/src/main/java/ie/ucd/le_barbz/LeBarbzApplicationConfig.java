package ie.ucd.le_barbz;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ie.ucd.le_barbz.model.Cart;
import ie.ucd.le_barbz.model.Customer;
import ie.ucd.le_barbz.repository.CartRepository;
import ie.ucd.le_barbz.repository.CustomerRepository;

@Configuration
public class LeBarbzApplicationConfig implements WebMvcConfigurer{

  @Autowired
  private CartRepository cartRepository;
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  

  @Bean
  public JavaMailSender getJavaMailSender() {
    JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
    javaMailSenderImpl.setHost("smtp.gmail.com");
    javaMailSenderImpl.setUsername("le.barbz.30860");
    javaMailSenderImpl.setPassword("supersecurepasswordforourecommerceplatform");
    javaMailSenderImpl.setPort(587);
    
    Properties mailProperties = new Properties();
    mailProperties.put("mail.smtp.starttls.enable", true);
    mailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    javaMailSenderImpl.setJavaMailProperties(mailProperties);

    return javaMailSenderImpl;
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry){
    registry.addViewController("/login").setViewName("login");
    registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
  }

  @PostConstruct
  private void createDefaultAdminCart(){
    final Cart cart = new Cart();
    cart.setProductCounts(new HashMap<>());
    cart.setProducts(new LinkedList<>());
    cartRepository.save(cart);
    createDefaultAdmin(cart);
  }

  private void createDefaultAdmin(Cart adminCart){
    final String encoded_password = passwordEncoder.encode("chef");

    if (customerRepository.findByUserName("remy").isPresent()) {
      return;
    }else{
      Customer customer = new Customer("Remy", "Linguini", "remy@gusteaus.fr", "remy", encoded_password, "33160304050", 
      adminCart);
      customerRepository.save(customer);
      adminCart.setCustomer(customer);
      cartRepository.save(adminCart);
    }
  }
  
}
