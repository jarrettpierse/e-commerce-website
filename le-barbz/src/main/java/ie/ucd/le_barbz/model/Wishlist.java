package ie.ucd.le_barbz.model;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

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
public class Wishlist implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "WISHLIST_PRODUCTS")
    private List<Product> products;

    @ToString.Exclude
    @OneToOne(mappedBy = "wishlist")
    private Customer customer;

    public void add(Product product) {
        if(products.contains(product)){
            return;
        }
        products.add(product);
    }

    public void remove(Product product){
        products.remove(product);
    }

    public void changeWishlists(Wishlist wishlist){
        this.products = wishlist.products;
    }

    public static Wishlist mergeWishlists(Wishlist guestWishlist, Wishlist customerWishlist) {
        final Wishlist mergedWishlist = new Wishlist();

        List<Product> mergedProducts = new LinkedList<>();
        mergedProducts.addAll(guestWishlist.getProducts());
        mergedProducts.addAll(customerWishlist.getProducts());
        mergedProducts = mergedProducts.parallelStream().distinct().collect(Collectors.toList());

        mergedWishlist.setProducts(mergedProducts);
        return mergedWishlist;
    }
}
