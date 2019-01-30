package com.dev.magazina.controller;

import com.dev.magazina.model.Category;
import com.dev.magazina.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String index(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "category/index";
    }

    @GetMapping("/categories/create")
    public String create(Model model) {
        if (!model.containsAttribute("category")) {
            model.addAttribute("category", new Category());
        }
        return "category/form";
    }

    @PostMapping("/categories/create")
    public String store(@Valid Category category, BindingResult result,
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.category", result);
            redirectAttributes.addFlashAttribute("category", category);
            return "redirect:/categories/create";
        }

        redirectAttributes.addFlashAttribute("flash", "Cat added successfully!");
        categoryService.save(category);
        return "redirect:/categories";
    }

    @PostMapping("/categories/{categoryId}/delete")
    public String delete(@PathVariable int categoryId, RedirectAttributes redirectAttributes) {
        Category category = categoryService.findById(categoryId);

        if (category.getProducts().size() > 1) {
            redirectAttributes.addFlashAttribute("flash", "Only empty categories can be deleted!");
            return "redirect:/categories"; //nqs ka nje produkt ose me shume nuk mund te fshihet dhe kthehet forma sic ishte
        }

        categoryService.delete(category); //ka 0 produkte
        redirectAttributes.addFlashAttribute("flash", "Category deleted!");
        return "redirect:/categories";
    }
}







