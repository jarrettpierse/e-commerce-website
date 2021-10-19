package ie.ucd.le_barbz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ie.ucd.le_barbz.model.Product;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByNameContaining(String name);

    @Query("SELECT p.id FROM Product p where p.type = :type")
    List<Integer> findByType(@Param("type") String type);

    @Query("SELECT p.id FROM Product p where p.type = :type and p.id != :id")
    List<Integer> findSimilar(@Param("type") String type, @Param("id") Integer id);
}
