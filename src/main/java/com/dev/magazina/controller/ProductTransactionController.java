package com.dev.magazina.controller;

import com.dev.magazina.model.*;
import com.dev.magazina.service.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/* import org.json.simple.JSONObject; */

@Controller
public class ProductTransactionController extends BaseController {
    @Autowired
    private ProductTransactionService supplyService;
    @Autowired
    private ProductService productService;
    @Autowired
    private MeasuringUnitService measuringUnitService;
    @Autowired
    private WarehouseProductService warehouseProductService;

    @Autowired
    private AgentService agentService;

    @Autowired
    private ProductTransactionUnitService supplyUnitService;

    @GetMapping("/supplies")
    public String index(Model model) {
        List<ProductTransaction> supply = supplyService.findByType("F");
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


    @RequestMapping(value = "/supplies/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String saveSupply(@RequestBody String requestobject) throws IOException, ParseException, java.text.ParseException {
        JSONObject result = new JSONObject();
        Product product = null;
        double amount = 0;
        ProductTransaction pt = new ProductTransaction();
        pt.setType("F");
        List<ProductTransactionUnit> productTransactionUnits = new ArrayList<ProductTransactionUnit>();
        JSONObject jsonObject = new JSONObject(requestobject);
        Set<String> keyList = jsonObject.keySet();
        Map<String, Object> jsonMap = jsonObject.toMap();
        String date = (String) jsonMap.get("date");
        Object invoiceNumber = jsonMap.get("invoiceNumber");
        SimpleDateFormat dateFormaterr = new SimpleDateFormat("yyyy-MM-dd");
        Date dateValue = dateFormaterr.parse(date);
        pt.setDate(dateValue);
        pt.setInvoiceNumber((String) invoiceNumber);
        supplyService.save(pt);
        Object ptus = jsonObject.get("ptus");
        List<Object> jsonArray = new JSONArray(ptus.toString()).toList();
        for (int index = 0; index < jsonArray.size(); index++) {
            HashMap<String, Object> map = (HashMap<String, Object>) jsonArray.get(index);
            ProductTransactionUnit ptu = new ProductTransactionUnit();
            product = productService.findById(Integer.parseInt(map.get("productId").toString()));
            amount = Double.parseDouble(map.get("amount").toString());
            ptu.setProduct(product);
            ptu.setAmount(amount);
            ptu.setPrice(Double.parseDouble(map.get("price").toString()));
            ptu.setProductTransaction(pt);
            supplyUnitService.save(ptu);
            productTransactionUnits.add(ptu);
            pt.setProductTransactionUnits(productTransactionUnits);

        }
        supplyService.save(pt);
        saveWareHouseProduct(product, amount);
        result.put("success", true);

        return "redirect:supplies";
    }

    private void saveWareHouseProduct(Product product, double amount) {
        WarehouseProduct wp = new WarehouseProduct();
        Warehouse warehouse = getWarehouse();
        wp.setAmount(amount);
        wp.setProduct(product);
        wp.setWarehouse(warehouse);
        if(warehouseProductService.compare(wp)){

        }
        else{
            warehouseProductService.save(wp);
        }



    }

    @RequestMapping(value = "/get/suppliers/products", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public HashMap<String, Object> getProducts() {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        List<Object> productList = new ArrayList<>();
        List<Product> products = productService.findAll();
        for (Product product : products) {
            HashMap<String, Object> muMap = new HashMap<String, Object>();
            muMap.put("id", product.getId());
            muMap.put("name", product.getName());
            muMap.put("measurinUnit", product.getMeasuringUnit().getSymbol());
            muMap.put("price", product.getPrice());
            productList.add(muMap);
        }
        resultMap.put("res", productList);
        return resultMap;

    }

    @RequestMapping(value = "/get/all/suppliers", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public HashMap<String, Object> getSuppliers() {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        List<Object> suppliersList = new ArrayList<>();
        List<Agent> agents = agentService.findByType("S");
        for (Agent agent : agents) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", agent.getId());
            map.put("businessName", agent.getBusinessName());
            suppliersList.add(map);
        }
        resultMap.put("result", suppliersList);
        return resultMap;

    }

}
