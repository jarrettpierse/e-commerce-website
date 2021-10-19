package ie.ucd.le_barbz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import ie.ucd.le_barbz.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
  Optional<Customer> findByUserName(String userName);
}
