package ie.ucd.le_barbz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import ie.ucd.le_barbz.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {
  public Optional<Cart> findByCustomerUserName(String username);
}
