package ie.ucd.le_barbz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ie.ucd.le_barbz.repository.ProductRepository;

@Controller
public class SearchController {
    @Autowired
    private ProductRepository productRepository;
  
    @GetMapping("/search/{name}")
    public String search(@PathVariable String name, Model model) {
        model.addAttribute("results", productRepository.findByNameContaining(name));
        return "search.html";
    }

    @GetMapping("/search")
    public String search() {
        return "search.html";
    }
}