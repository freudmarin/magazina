package com.dev.magazina.controller;

import com.dev.magazina.model.Warehouse;
import com.dev.magazina.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Controller
@Secured("ROLE_ADMIN")
public class WarehouseController extends BaseController {
    @Autowired
    private WarehouseService warehouseService;

    @GetMapping("/warehouses")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ModelAndView index(ModelAndView modelAndView) {
       Warehouse currentWarehouse = getWarehouse();

        List<Warehouse> warehouses = warehouseService.findAll();
        modelAndView.setViewName("warehouse/index");
        modelAndView.getModel().put("warehouses", warehouses);
        modelAndView.getModel().put("currentWarehouse", currentWarehouse);
        return modelAndView;
    }

    @RequestMapping(value = "/get/warehouses", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public HashMap<String, String> getWarehouses() {
        HashMap<String, String> resultMap = new HashMap<String, String>();
        List<Warehouse> warehouses = warehouseService.findAll();
        Gson gson = new Gson();
        String jsonList = gson.toJson(warehouses);

        resultMap.put("res", jsonList);

        return resultMap;
    }

    @GetMapping("/warehouses/create")
    public String create(Model model) {
        Warehouse currentWarehouse = getWarehouse();
        model.addAttribute("currentWarehouse", currentWarehouse);

        if (!model.containsAttribute("warehouse")) {
            model.addAttribute("warehouse", new Warehouse());
        }

        model.addAttribute("heading","Shto nje Magazine");
        model.addAttribute("submit","Shto");
        model.addAttribute("action", "/warehouses/create");
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
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public String checkout(HttpServletRequest request, @PathVariable int warehouseId) {
        setWarehouse(warehouseService.findById(warehouseId));
        return "redirect:" + request.getHeader("referer");
    }

    @GetMapping("/warehouses/{warehouseId}/edit")
    public String editWarehouse(@PathVariable int warehouseId, Model model) {
        if (!model.containsAttribute("warehouse")) {
            model.addAttribute("warehouse", warehouseService.findById(warehouseId));
        }
        model.addAttribute("action", "/warehouses/edit");
        model.addAttribute("heading","Modifiko Magazinen");
        model.addAttribute("submit","Modifiko");
        return "warehouse/form";
    }

    @PostMapping("/warehouses/edit")
    public String updateWarehouse(@Valid Warehouse warehouse, BindingResult result, RedirectAttributes redirectAttributes) {
        Warehouse existingWarehouse = warehouseService.findByName(warehouse.getName());
        if (result.hasErrors() ||
                existingWarehouse != null && warehouse.getId() != existingWarehouse.getId()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.warehouse", result);
            redirectAttributes.addFlashAttribute("warehouse", warehouse);
            if (existingWarehouse != null && warehouse.getId() != existingWarehouse.getId()) {
                redirectAttributes.addFlashAttribute("flash", "Magazina ekziston!");
            }
            return String.format("redirect:/warehouses/%s/edit", warehouse.getId());
        }

        warehouseService.save(warehouse);
        redirectAttributes.addFlashAttribute("flash", "Magazina u modifikua me sukses!");
        return "redirect:/warehouses";
    }
}

