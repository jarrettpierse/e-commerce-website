package ie.ucd.le_barbz.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.transaction.Transactional;

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
@Transactional
@EqualsAndHashCode
public class Cart implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "CART_PRODUCTS")
    private List<Product> products;

    @Column(length = 3000)
    private HashMap<Integer, Integer> productCounts = new HashMap<>();

    @ToString.Exclude
    @OneToOne(mappedBy = "cart", fetch = FetchType.EAGER)
    private Customer customer;

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public void add(Product product) {
        add(product, 1);
    }

    public void add(Product product, int quantity) {
        if (products.parallelStream().anyMatch(e -> e.getId() == product.getId())) {
            productCounts.put(product.getId(), productCounts.get(product.getId())  + quantity );
        } else {
            products.add(product);
            productCounts.put(product.getId(), quantity);
        }
    }

    public void remove(Product product) {
        products.remove(product);
        productCounts.remove(product.getId());
    }

    public void changeCarts(Cart cart){
        this.products = cart.products;
        this.productCounts = cart.productCounts;
    }

    public void quantityChange(Product product, int quantity) {
        productCounts.put(product.getId(), quantity );
    }


    public double getCost() {
        if(products.isEmpty()){
            return 0.00;
        }

        return products.parallelStream().map(p -> p.getPrice() * productCounts.get(p.getId())).reduce(Double::sum)
                .get();
    }

    public boolean moveToOrder(Cart cart) {
        if(cart.isEmpty()) {
            return false; 
        }

        ShopOrder orderToAdd = new ShopOrder();
        orderToAdd.openOrder();
        return true;
    }

    public HashMap<Double, Double> getOriginalPrices(){
        HashMap<Double, Double> productPrices = new HashMap<Double, Double>();
        for(Product p : products){
            productPrices.put((double) p.getId(),p.getPrice());
        }
        return productPrices;
    }

    /**
     * Merge a pair of carts.
     * Returned cart will have ID unset.
     * Returned cart will have customer unset.
     */
    public static Cart mergeCarts(Cart guestCart, Cart customerCart) {
        final Cart mergedCart = new Cart();

        List<Product> mergedProducts = new LinkedList<>();
        mergedProducts.addAll(guestCart.getProducts());
        mergedProducts.addAll(customerCart.getProducts());
        mergedProducts = mergedProducts.parallelStream().distinct().collect(Collectors.toList());

        HashMap<Integer, Integer> mergedProductCounts = (HashMap<Integer, Integer>) guestCart.getProductCounts().clone();
        customerCart.getProductCounts().forEach((key, value) -> {
            if (mergedProductCounts.containsKey(key)) {
                mergedProductCounts.put(key, customerCart.getProductCounts().get(key) + mergedProductCounts.get(key));
            } else {
                mergedProductCounts.put(key, customerCart.getProductCounts().get(key));
            }
        });


        mergedCart.setProducts(mergedProducts);
        mergedCart.setProductCounts(mergedProductCounts);
        return mergedCart;
    }

    public void empty(){
        products.clear();
        new HashMap<>();
    }
}
