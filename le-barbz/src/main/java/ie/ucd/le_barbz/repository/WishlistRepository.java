package ie.ucd.le_barbz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import ie.ucd.le_barbz.model.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {
  public Optional<Wishlist> findByCustomerUserName(String username);
}
