package ie.ucd.le_barbz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ie.ucd.le_barbz.model.ShopOrder;

public interface ShopOrderRepository extends JpaRepository<ShopOrder, Integer> {

}
