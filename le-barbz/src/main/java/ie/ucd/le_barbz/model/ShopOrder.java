package ie.ucd.le_barbz.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import java.util.HashMap;
import java.util.LinkedList;
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
public class ShopOrder implements Serializable{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column
  private OrderState state;

  /* Prices charged for each product at time of sale.
   * Maps Product Id's to Prices
   * Should always be used instead of the Product's price as tracked in the database.
   */
  @Column(length = 3000)
  HashMap<Double, Double> pricesCharged;

  private double totalCost;

  @ToString.Exclude
  @ManyToMany
  @JoinTable(name = "ORD_PRODUCT")
  private List<Product> products;

  @ManyToOne
  private Customer customer;

  @Column(length = 3000)
  private HashMap<Integer, Integer> units;

  public enum OrderState {
    NEW,
    SHIPPED,
    DELIVERED,
    CANCELLED
  }

  public boolean sameState(String givenStatus) {
    OrderState orderStatus = OrderState.valueOf(givenStatus);
    return this.state == orderStatus;
  }

  public void getOrderFromCart(Cart cart) {
    openOrder();
    products = new LinkedList<Product>(cart.getProducts());
    pricesCharged = new HashMap<Double, Double>(cart.getOriginalPrices());
    units = new HashMap<Integer, Integer>(cart.getProductCounts());
    totalCost = cart.getCost();
  }

  public void closeOrder(){
    this.state = OrderState.CANCELLED;
  }

  public void inTransitOrder() {
    this.state = OrderState.SHIPPED;
  }

  public void openOrder() {
    this.state = OrderState.NEW;
  }

  public void deliveredOrder() {
    this.state = OrderState.DELIVERED;
  }
}
