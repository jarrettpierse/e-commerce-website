package ie.ucd.le_barbz.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Product implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String type;

    @Column
    private int quantity;

    @Column
    private double price;

    @Column
    private String imageURL;

    @Column(columnDefinition = "boolean default false")
    private Boolean hidden;

    @ToString.Exclude
    @ManyToMany(mappedBy = "products")
    private List<Cart> carts;

    @ToString.Exclude
    @ManyToMany(mappedBy = "products")
    private List<Wishlist> wishlist;

    @ToString.Exclude
    @ManyToMany(mappedBy = "products")
    private List<ShopOrder> shopOrders;

    public void updateDetails(Product product){
        setName(product.name);
        setDescription(product.description);
        setType(product.type);
        setQuantity(product.quantity);
        setPrice(product.price);
    }
}
