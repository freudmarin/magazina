package com.dev.magazina.controller;

import com.dev.magazina.model.Category;
import com.dev.magazina.service.CategoryService;
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
        model.addAttribute("heading"," Shto Kategorine");
        model.addAttribute("submit","Shto");
        model.addAttribute("action",String.format("/categories/create"));
        return "category/form";
    }

    @PostMapping("/categories/create")
    public String store(@Valid Category category, BindingResult result,
                        RedirectAttributes redirectAttributes) {
        Category existingCategory = categoryService.findByName(category.getName());

        if (result.hasErrors() || existingCategory != null) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.category", result);
            redirectAttributes.addFlashAttribute("category", category);
            if (existingCategory != null) {
                redirectAttributes.addFlashAttribute("flash", "Kategoria ekziston!");
            }  //flash ndodhet ne faqen html
            return "redirect:/categories/create";
        }

        redirectAttributes.addFlashAttribute("flash", "Kategoria u shtua me sukses!");
        categoryService.save(category);
        return "redirect:/categories";
    }

    @PostMapping("/categories/{categoryId}/delete")
    public String delete(@PathVariable int categoryId, RedirectAttributes redirectAttributes) {
        Category category = categoryService.findById(categoryId);

        if (category.getProducts().size() > 1) {
            redirectAttributes.addFlashAttribute("flash", "Vetem kategorite pa produkte mund te fshihen!");
            return "redirect:/categories"; //nqs ka nje produkt ose me shume nuk mund te fshihet dhe kthehet forma sic ishte
        }

        categoryService.delete(category); //ka 0 produkte
        redirectAttributes.addFlashAttribute("flash", "Kategoria u fshi!");
        return "redirect:/categories";
    }

   @GetMapping("/categories/{categoryId}/edit")
   public String edit(Model model,RedirectAttributes redirectAttributes,@PathVariable Integer categoryId) {
       if (!model.containsAttribute("category")) {
           model.addAttribute("category",categoryService.findById(categoryId));
       }
      //flash ndodhet ne faqen html

       model.addAttribute("action",String.format("/categories/%s",categoryId));
       model.addAttribute("heading"," Modifiko Kategorine");
       model.addAttribute("submit","Modifiko");
       return "category/form";
   }
    @RequestMapping(value = "/categories/{categoryId}", method = RequestMethod.POST)
    public String update(@Valid Category category ,RedirectAttributes redirectAttributes, BindingResult result) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.category", result);
            redirectAttributes.addFlashAttribute("category", category);
          //  redirectAttributes.addFlashAttribute("flash","Nuk eshte modifikuar gje");
            return String.format("redirect:/categories/{%s}/edit",category.getId());
        }

//        Category category = categoryService.findById(categoryId);
//        model.addAttribute("categories", category);
        categoryService.save(category);
////        redirectAttributes.addFlashAttribute("flash", "Kategoria u editua!");
//        return "redirect:/categories";
        redirectAttributes.addFlashAttribute("flash", "Kategroia u modifikua");
        return "redirect:/categories";
   }
}







