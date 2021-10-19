package ie.ucd.le_barbz.service;

import java.util.HashMap;
import java.util.LinkedList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ie.ucd.le_barbz.model.Cart;
import ie.ucd.le_barbz.model.Wishlist;
import ie.ucd.le_barbz.repository.CartRepository;
import ie.ucd.le_barbz.repository.WishlistRepository;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class SessionService {
  @Autowired
  CartRepository cartRepository;

  @Autowired
  WishlistRepository wishlistRepository;

  public int getCartId(HttpSession session) {
    if (session.getAttribute("cart_id") == null) {
    session.setAttribute("cart_id", generateCartId());
    }

    return (int) session.getAttribute("cart_id");
  }

  public int generateCartId() {
    final Cart cart = new Cart();
    cart.setProductCounts(new HashMap<>());
    cart.setProducts(new LinkedList<>());
    return cartRepository.save(cart).getId();
  }

  public int getWishlistId(HttpSession session) {
    if (session.getAttribute("wishlist_id") == null) {
      session.setAttribute("wishlist_id", generateWishlistId());
    }

    return (int) session.getAttribute("wishlist_id");
  }

  private int generateWishlistId() {
    final Wishlist wishlist = new Wishlist();
    wishlist.setProducts(new LinkedList<>());
    return wishlistRepository.save(wishlist).getId();
  }
}
