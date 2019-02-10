package com.dev.magazina.controller;

import com.dev.magazina.model.Product;
import com.dev.magazina.model.ProductTransaction;
import com.dev.magazina.model.ProductTransactionUnit;
import com.dev.magazina.service.ProductService;
import com.dev.magazina.service.ProductTransactionService;
import com.dev.magazina.service.ProductTransactionUnitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
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
            ProductTransaction pt = new ProductTransaction();
            ProductTransactionUnit ptu = new ProductTransactionUnit(new Product(), 1, 1, pt);

            pt.setProductTransactionUnits(Arrays.asList(ptu));

            model.addAttribute("supply", pt);
        }

        model.addAttribute("products", productService.findAll());

        return "supply/form";
    }

    //    mos e di si e ka konvertimin?proveka
    @RequestMapping(value = "/supplies/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String test(@RequestBody Object request) throws IOException {
        ProductTransaction pt = new ProductTransaction();
//        ObjectMapper mapper = new ObjectMapper();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.convertValue(request,HashMap.class);
//        Object myMap = objectMapper.readValue(request, HashMap.class);


//        JsonObject objectFromString = jsonParser.parse(request).getAsJsonObject();

        return "" + request;
    }

//    @RequestMapping(value = "/supplies/add")
//    public String addRow(final ProductTransaction pt, ProductTransactionUnit ptu, Model model) {
////        ProductTransactionUnit ptu = new ProductTransactionUnit();
////        pt.getProductTransactionUnits().add(ptu);
////        model.addAttribute("productTransaction", pt);
//        return "redirect:/product-transaction/supplies";
//    }


}
