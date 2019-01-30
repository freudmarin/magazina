package com.dev.magazina.controller;

import com.dev.magazina.model.Warehouse;
import com.dev.magazina.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String create(@RequestParam(value = "warehouseId", required = false) Integer warehouseId, Model model) {
        Warehouse currentWarehouse = warehouseService.findById(warehouseId);
        model.addAttribute("currentWarehouse", currentWarehouse);

        if (!model.containsAttribute("warehouse")) {
            model.addAttribute("warehouse", new Warehouse());
        }
        return "warehouse/form";
    }

    @PostMapping("/warehouses/create")
    public String store(@RequestParam(value = "warehouseId", required = false) Integer warehouseId,
                        @Valid Warehouse warehouse, BindingResult result,
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.warehouse", result);
            redirectAttributes.addFlashAttribute("warehouse", warehouse);
            return "redirect:/warehouses/create/?" + getWarehouseParam(warehouseId);
        }

        redirectAttributes.addFlashAttribute("flash", "Warehouse added successfully!");
        warehouseService.save(warehouse);
        return "redirect:/warehouses/?" + getWarehouseParam(warehouseId);
    }

    @PostMapping("/warehouses/{deleteWarehouseId}/delete")
    public String delete(@RequestParam(value = "warehouseId", required = false) Integer currentWarehouseId,
            @PathVariable int deleteWarehouseId, RedirectAttributes redirectAttributes) {
        Warehouse currentWarehouse = warehouseService.findById(currentWarehouseId);
        Warehouse warehouse = warehouseService.findById(deleteWarehouseId);

        if (currentWarehouse.getId() == deleteWarehouseId) {
            redirectAttributes.addFlashAttribute("flash", "You cannot delete your current warehouse!");
            return "redirect:/warehouses/?" + getWarehouseParam(currentWarehouseId);
        }

        warehouseService.delete(warehouse);
        redirectAttributes.addFlashAttribute("flash", "Warehouse deleted!");
        return "redirect:/warehouses/?" + getWarehouseParam(currentWarehouseId);
    }

    private String getWarehouseParam(Integer warehouseId) {
        return  warehouseId != null ? "warehouseId=" + warehouseId : "";
    }
}

