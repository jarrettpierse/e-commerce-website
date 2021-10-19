package ie.ucd.le_barbz.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String firstname;

    @Column
    private String surname;

    @Column
    private String email;

    @Column(unique = true)
    private String userName;

    @Column
    private String password;

    @Column
    private String phone;

    @Column
    private String addressLine1;

    @Column
    private String addressLine2;

    @Column
    private String city;

    @Column
    private String county;

    @Column
    private String country;

    @Column
    private String postalCode;

    @Column
    private boolean active;

    @Column
    private String roles;

    @JsonIgnore
    @ToString.Exclude
    @OneToOne(orphanRemoval = true)
    private Cart cart;

    @JsonIgnore
    @ToString.Exclude
    @OneToOne(orphanRemoval = true)
    private Wishlist wishlist;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "customer")
    private List<ShopOrder> shopOrders;

    public Customer(String firstname, String surname, String email, String userName, String password,
            String addressLine1, String addressLine2, String city, String county, String country, String postalCode,
            String phone, Cart cart, Wishlist wishlist) {
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.userName = userName;
        this.password = password;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.county = county;
        this.country = country;
        this.postalCode = postalCode;
        this.cart = cart;
        this.wishlist = wishlist;
        this.active = true;
        this.roles = "USER";
    }

    public Customer(String firstname, String surname, String email, String userName, String password, String phone, Cart cart) {
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.userName = userName;
        this.password = password;
        this.cart = cart;
        this.wishlist = null;
        this.active = true;
        this.roles = "ADMIN";
    }
}
