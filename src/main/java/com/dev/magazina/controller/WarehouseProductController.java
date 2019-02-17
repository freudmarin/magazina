package com.dev.magazina.controller;

import com.dev.magazina.model.Warehouse;
import com.dev.magazina.model.WarehouseProduct;
import com.dev.magazina.service.WarehouseService;
import com.dev.magazina.service.WarehouseProductService;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@Secured("ROLE_ADMIN")
public class WarehouseProductController extends BaseController {
    @Autowired
    private WarehouseProductService warehouseProductService;

    @GetMapping("/available-products")
    public String index(Model model) {
        List<WarehouseProduct> wps = warehouseProductService.findById(getWarehouse().getId());
        model.addAttribute("warehouseProducts", wps);
        return "warehouse_product/availableProducts";
    }
}

