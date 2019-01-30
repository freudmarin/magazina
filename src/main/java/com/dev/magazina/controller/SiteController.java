package com.dev.magazina.controller;

import com.dev.magazina.model.Warehouse;
import com.dev.magazina.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SiteController extends BaseController {
    @Autowired
    private WarehouseService warehouseService;

    @GetMapping(value = {"", "/"})
    public String home(Model model) {
        Warehouse currentWarehouse = getWarehouse();

        List<Warehouse> warehouses = warehouseService.findAll();

        model.addAttribute("warehouses", warehouses);
        model.addAttribute("currentWarehouse", currentWarehouse);
        return "site/dashboard";
    }
}
