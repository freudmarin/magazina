package com.dev.magazina.controller;

import com.dev.magazina.model.Warehouse;
import com.dev.magazina.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class WarehouseController extends BaseController {
    @Autowired
    private WarehouseService warehouseService;

    @GetMapping("/warehouses")
    public ModelAndView index(ModelAndView modelAndView) {
       Warehouse currentWarehouse = getWarehouse();

        List<Warehouse> warehouses = warehouseService.findAll();
        modelAndView.setViewName("warehouse/index");
        modelAndView.getModel().put("warehouses", warehouses);
        modelAndView.getModel().put("currentWarehouse", currentWarehouse);
        return modelAndView;
    }

    @GetMapping("/warehouses/create")
    public String create(Model model) {
        Warehouse currentWarehouse = getWarehouse();
        model.addAttribute("currentWarehouse", currentWarehouse);

        if (!model.containsAttribute("warehouse")) {
            model.addAttribute("warehouse", new Warehouse());
        }
        return "warehouse/form";
    }

    @PostMapping("/warehouses/create")
    public String store(@Valid Warehouse warehouse, BindingResult result,
                        RedirectAttributes redirectAttributes) {
        Warehouse existingWarehouse = warehouseService.findByName(warehouse.getName());
        if (result.hasErrors() || existingWarehouse != null) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.warehouse", result);
            redirectAttributes.addFlashAttribute("warehouse", warehouse);
            if (existingWarehouse != null) {
                redirectAttributes.addFlashAttribute("flash", "Magazina ekziston!");
            }
            return "redirect:/warehouses/create";
        }

        warehouseService.save(warehouse);
        redirectAttributes.addFlashAttribute("flash", "Magazina u shtua me sukses!");
        return "redirect:/warehouses";
    }

    @PostMapping("/warehouses/{deleteWarehouseId}/delete")
    public String delete(@PathVariable int deleteWarehouseId, RedirectAttributes redirectAttributes) {
        Warehouse currentWarehouse = getWarehouse();
        Warehouse warehouse = warehouseService.findById(deleteWarehouseId);

        if (currentWarehouse.getId() == deleteWarehouseId) {
            redirectAttributes.addFlashAttribute("flash", "Nuk mund të fshini magazinën aktuale!");
            return "redirect:/warehouses";
        }

        warehouseService.delete(warehouse);
        redirectAttributes.addFlashAttribute("flash", "Magazina u fshi me sukses!");
        return "redirect:/warehouses";
    }

    @GetMapping("/warehouses/checkout/{warehouseId}")
    public String checkout(HttpServletRequest request, @PathVariable int warehouseId) {
        setWarehouse(warehouseService.findById(warehouseId));
        return "redirect:" + request.getHeader("referer");
    }
}

