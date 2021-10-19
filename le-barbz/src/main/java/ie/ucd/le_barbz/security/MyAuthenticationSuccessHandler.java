package ie.ucd.le_barbz.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;

import ie.ucd.le_barbz.model.Cart;
import ie.ucd.le_barbz.repository.CartRepository;
import ie.ucd.le_barbz.repository.CustomerRepository;
import ie.ucd.le_barbz.repository.WishlistRepository;
import ie.ucd.le_barbz.service.SessionService;
import ie.ucd.le_barbz.model.MyUserDetails;
import ie.ucd.le_barbz.model.Wishlist;

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  @Autowired
  private SessionService sessionService;
  @Autowired
  private CartRepository cartRepository;
  @Autowired
  private WishlistRepository wishlistRepository;
  @Autowired
  private CustomerRepository customerRepository;

  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  @Transactional
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
        final HttpSession session = request.getSession();
    
        Cart mergedCart = Cart.mergeCarts(cartRepository.getOne(sessionService.getCartId(session)), customerRepository.findByUserName(
            ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())
        .get().getCart());

        if(!( customerRepository.findByUserName(((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())
      .get().getRoles().contains("ADMIN"))){
          Wishlist mergedWishlist = Wishlist.mergeWishlists(wishlistRepository.getOne(sessionService.getWishlistId(session)), customerRepository.findByUserName(
            ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())
          .get().getWishlist());

          customerRepository.findByUserName(
            ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())
        .get().getWishlist().changeWishlists(mergedWishlist);

        request.getSession().setAttribute("wishlist_id", customerRepository.findByUserName(
          ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())
      .get().getWishlist().getId());
        }
       
        customerRepository.findByUserName(
            ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())
        .get().getCart().changeCarts(mergedCart);

        request.getSession().setAttribute("cart_id", customerRepository.findByUserName(
          ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())
      .get().getCart().getId());

        redirectStrategy.sendRedirect(request, response, "/");
  }
}
