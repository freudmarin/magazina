package com.dev.magazina.controller;

import com.dev.magazina.model.ProductTransaction;
import com.dev.magazina.model.ProductTransactionUnit;
import com.dev.magazina.service.ProductService;
import com.dev.magazina.service.ProductTransactionService;
import com.dev.magazina.service.ProductTransactionUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductTransactionController {
    @Autowired
    private ProductTransactionService supplyService;
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductTransactionUnitService supplyUnitService;

    @GetMapping("/supplies")
    public String index(Model model) {
        List<ProductTransaction> supply = supplyService.findAll();
        model.addAttribute("supplies", supply);
        return "supply/list";
    }

    @GetMapping("/supplies/create")
    public String create(Model model) {
        if (!model.containsAttribute("supply")) {
            model.addAttribute("supply", new ProductTransaction());
            model.addAttribute("products", productService.findAll());
        }
        return "supply/form";
    }


    @PostMapping("/supplies/create")
    public String store(Model model, @Valid ProductTransaction supply, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productTransaction", result);
            redirectAttributes.addFlashAttribute("supply", supply);
            return "redirect:/supplies/create";
        }
        redirectAttributes.addFlashAttribute("flash", "Furnizimi u krijua me sukses!");
        supplyService.save(supply);
        return "redirect:/supplies";

    }

//    @RequestMapping(value = "/supplies/add")
//    public String addRow(final ProductTransaction pt, ProductTransactionUnit ptu, Model model) {
////        ProductTransactionUnit ptu = new ProductTransactionUnit();
////        pt.getProductTransactionUnits().add(ptu);
////        model.addAttribute("productTransaction", pt);
//        return "redirect:/product-transaction/supplies";
//    }


}
