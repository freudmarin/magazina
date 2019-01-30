package com.dev.magazina.controller;

import com.dev.magazina.model.Warehouse;
import com.dev.magazina.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Controller
class BaseController {
    @Autowired
    private WarehouseService warehouseService;

    public Warehouse getWarehouse() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        Warehouse warehouse = (Warehouse) session.getAttribute("currentWarehouse");

        if (warehouse == null) {
            return warehouseService.findFirst();
        }

        return warehouse;
    }
}
