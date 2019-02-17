package com.dev.magazina.controller;

import com.dev.magazina.model.Category;
import com.dev.magazina.model.Product;
import com.dev.magazina.service.CategoryService;
import com.dev.magazina.service.MeasuringUnitService;
import com.dev.magazina.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MeasuringUnitService measuringUnitService;

    @GetMapping("/products")
    public String index(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);//kalon listen e produkteve tek view-ja products
        return "product/index";
    }

    @GetMapping("/products/create")
    public String create(Model model) {
        if (!model.containsAttribute("product")) {
            model.addAttribute("product", new Product());
        }
        model.addAttribute("categories", categoryService.findAll()); //i kalojme variablit produkt vlerat nga databaza
        model.addAttribute("measuringUnits", measuringUnitService.findAll());
        model.addAttribute("heading","Shto Produktin");
        model.addAttribute("submit","Shto");
        model.addAttribute("action", "/products/create");
        return "product/form";
    }

    @PostMapping("/products/create")
    public String store(@Valid Product product, BindingResult result,
                        RedirectAttributes redirectAttributes) {
        Product existingProduct = productService.findByName(product.getName());
        if (result.hasErrors() || existingProduct != null) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.product", result);
            redirectAttributes.addFlashAttribute("product", product);
            if (existingProduct != null) {
                redirectAttributes.addFlashAttribute("flash", "Produkti ekziston!");
            }
            return "redirect:/products/create";
        }

        productService.save(product);
        redirectAttributes.addFlashAttribute("flash", "Produkti u shtua me sukses!");

        return "redirect:/products";
    }

    @PostMapping("/products/{productId}/delete")
    public String delete(@PathVariable int productId, RedirectAttributes redirectAttributes) {
        Product product = productService.findById(productId);
        productService.delete(product);
        redirectAttributes.addFlashAttribute("flash", "Produkti u fshi me sukses!");
        return "redirect:/products";
    }

    @GetMapping("/products/{productId}/edit")
    public String editProduct(@PathVariable int productId, Model model) {
        if (!model.containsAttribute("product")) {
            model.addAttribute("product", productService.findById(productId));
        }
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("measuringUnits", measuringUnitService.findAll());
        model.addAttribute("action", "/products/edit");
        model.addAttribute("heading","Modifiko Produktin");
        model.addAttribute("submit","Modifiko");
        return "product/form";
    }

    @PostMapping("/products/edit")
    public String updateProduct(@Valid Product product, BindingResult result, RedirectAttributes redirectAttributes) {
        Product existingProduct = productService.findByName(product.getName());
        if (result.hasErrors() ||
                existingProduct != null && product.getId() != existingProduct.getId()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.product", result);
            redirectAttributes.addFlashAttribute("product", product);
            if (existingProduct != null && product.getId() != existingProduct.getId()) {
                redirectAttributes.addFlashAttribute("flash", "Produkti ekziston!");
            }
            return String.format("redirect:/products/%s/edit", product.getId());
        }

        productService.save(product);
        redirectAttributes.addFlashAttribute("flash", "Produkti u modifikua me sukses!");
        return "redirect:/products";
    }
}
