package ie.ucd.le_barbz.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import ie.ucd.le_barbz.Utility.FileUploadUtil;
import ie.ucd.le_barbz.model.Cart;
import ie.ucd.le_barbz.model.Product;
import ie.ucd.le_barbz.model.Wishlist;
import ie.ucd.le_barbz.repository.CartRepository;
import ie.ucd.le_barbz.repository.ProductRepository;
import ie.ucd.le_barbz.repository.WishlistRepository;
import ie.ucd.le_barbz.service.SessionService;

@Controller
public class ProductController {
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private CartRepository cartRepository;
  @Autowired
  private WishlistRepository wishlistRepository;
  @Autowired
  private SessionService sessionService;

  @GetMapping("/view-product/{id}")
  public String viewProduct(@PathVariable Integer id, Model model, HttpSession session) {
    model.addAttribute("product", productRepository.findById(id).get());
    // adding every other product of same type to the model
    model.addAttribute("products", productRepository
        .findAllById(productRepository.findSimilar(productRepository.findById(id).get().getType(), id)));
    final int wishlist_id = sessionService.getWishlistId(session);

    final Wishlist wishlist = wishlistRepository.getOne(wishlist_id);
    model.addAttribute("isInWishlist", wishlist.getProducts().contains(productRepository.findById(id).get()));
    return "view-product.html";
  }

  @GetMapping("/products")
  public String products(Model model) {
    model.addAttribute("products", productRepository.findAll());
    return "products.html";
  }

  @PostMapping("/cart/add")
  public @ResponseBody void addToCart(@RequestParam int id, @RequestParam int quantity, HttpSession session) {
    final int cart_id = sessionService.getCartId(session);
    final Cart cart = cartRepository.getOne(cart_id);
    final Product product = productRepository.findById(id).get();
    cart.add(product, quantity);
    cartRepository.save(cart);
  }

  @PostMapping("/wishlist/add")
  public @ResponseBody void addToWishlist(@RequestParam int id, HttpSession session) {
    final int wishlist_id = sessionService.getWishlistId(session);
    final Wishlist wishlist = wishlistRepository.getOne(wishlist_id);
    final Product product = productRepository.findById(id).get();
    wishlist.add(product);
    wishlistRepository.save(wishlist);
  }

  @PostMapping("/wishlist/remove")
  public @ResponseBody void removeFromWishlist(@RequestParam int id, HttpSession session) {
    final int wishlist_id = sessionService.getWishlistId(session);
    final Wishlist wishlist = wishlistRepository.getOne(wishlist_id);
    final Product product = productRepository.findById(id).get();
    wishlist.remove(product);
    wishlistRepository.save(wishlist);
  }

  @RequestMapping(value = "/products/{category}", method = RequestMethod.GET)
  public String viewByCategory(@PathVariable String category, Model model) {
    model.addAttribute("products", productRepository.findAllById(productRepository.findByType(category)));
    return "products.html";
  }

  @PostMapping("/product/save")
  public RedirectView saveUser(Product product, @RequestParam("image") MultipartFile multipartFile) throws IOException {
    String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
    product.setImageURL(fileName);
    productRepository.save(product);
    String uploadDir = "src/main/resources/static/images/products";
    FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
    return new RedirectView("/admin", true);
  }

  @PostMapping("/product/edit/{id}")
  public RedirectView editProduct(@PathVariable Integer id, Product product,
      @RequestParam("image") MultipartFile multipartFile) throws IOException {
    Product p = productRepository.findById(id).get();
    p.updateDetails(product);

    String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
    if (fileName.contains(".")) { // file was added
      p.setImageURL(fileName);
      String uploadDir = "src/main/resources/static/images/products";
      FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
    }

    productRepository.save(p);
    return new RedirectView("/admin/view-inventory", true);
  }

  @PostMapping("/product/hide")
  public @ResponseBody void hideProduct(@RequestParam int id) {
    Product product = productRepository.findById(id).get();
    product.setHidden(true);
    productRepository.save(product);
  }

  @PostMapping("/product/unhide")
  public @ResponseBody void unhideProduct(@RequestParam int id) {
    Product product = productRepository.findById(id).get();
    product.setHidden(false);
    productRepository.save(product);
  }
}