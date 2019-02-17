package com.dev.magazina.controller;

import com.dev.magazina.model.*;
import com.dev.magazina.service.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/* import org.json.simple.JSONObject; */

@Controller
@Secured("ROLE_ADMIN")
public class ProductTransactionController extends BaseController {
    @Autowired
    private ProductTransactionService supplyService;
    @Autowired
    private ProductService productService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private MeasuringUnitService measuringUnitService;
    @Autowired
    private WarehouseProductService warehouseProductService;

    @Autowired
    private ProductTransactionUnitService supplyUnitService;

    @GetMapping("/supplies")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public String index(Model model) {
        List<ProductTransaction> supply = supplyService.findByType("F", getWarehouse());
        model.addAttribute("supplies", supply);
        return "supply/index";
    }

    @GetMapping("/supplies/{supplyId}/details")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public String supplyDetails(@PathVariable int supplyId, RedirectAttributes redirectAttributes, Model model) {
        {
            double total = 0;
            ProductTransaction supply = supplyService.findById(supplyId);
            for (ProductTransactionUnit ptu : supply.getProductTransactionUnits()) {
                total += ptu.getAmount() * ptu.getPrice();
            }
            model.addAttribute("supply", supply);
            model.addAttribute("total", total);
            return "supply/details";
        }
    }

    @GetMapping("/sales/{saleId}/details")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public String saleDetails(@PathVariable int saleId, RedirectAttributes redirectAttributes, Model model) {
        {
            double total = 0;
            ProductTransaction sale = supplyService.findById(saleId);
            for (ProductTransactionUnit ptu : sale.getProductTransactionUnits()) {
                total += ptu.getAmount() * ptu.getPrice();
            }
            model.addAttribute("sale", sale);
            model.addAttribute("total", total);
            return "sale/details";
        }
    }

    @GetMapping("/sales")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public String saleIndex(Model model) {
        List<ProductTransaction> sales = supplyService.findByType("Sh", getWarehouse());
        model.addAttribute("sales", sales);
        return "sale/index";
    }

    @GetMapping("/supplies/create")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public String create(Model model) {
//        if (!model.containsAttribute("supply")) {
//            ProductTransaction pt = new ProductTransaction();
//            ProductTransactionUnit ptu = new ProductTransactionUnit(new Product(), 1, 1, pt);
//            pt.setProductTransactionUnits(Arrays.asList(ptu));
//            model.addAttribute("supply", pt);
//        }
//        model.addAttribute("products", productService.findAll());

        return "supply/form";
    }


    @RequestMapping(value = "/supplies/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public JSONObject saveSupply(@RequestBody String requestobject) throws
            IOException, ParseException, java.text.ParseException {
        JSONObject result = new JSONObject();
        Product product = null;
        double amount = 0;

        ProductTransaction pt = new ProductTransaction();
        pt.setType("F");
        Warehouse w = getWarehouse();
        pt.setWarehouse(getWarehouse());
        List<ProductTransactionUnit> productTransactionUnits = new ArrayList<ProductTransactionUnit>();
        JSONObject jsonObject = new JSONObject(requestobject);
        Set<String> keyList = jsonObject.keySet();
        Map<String, Object> jsonMap = jsonObject.toMap();
        String date = (String) jsonMap.get("date");
        Object invoiceNumber = jsonMap.get("invoiceNumber");
        Object supplier = jsonMap.get("supplier");
        HashMap mp = (HashMap) supplier;
        String str = mp.get("id").toString();
        int id = Integer.parseInt(str);
        //        int supplierId = mp.get("id");
//        String suppIdStr  = (String)supplierId;
//        int suppId = Integer.parseInt(suppIdStr);
        Agent agent = agentService.findById(id);
        SimpleDateFormat dateFormaterr = new SimpleDateFormat("yyyy-MM-dd");
        Date dateValue = dateFormaterr.parse(date);
        pt.setDate(dateValue);
        pt.setAgent(agent);
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
            saveWareHouseProduct(product, amount);
            productTransactionUnits.add(ptu);
            pt.setProductTransactionUnits(productTransactionUnits);

        }
        supplyService.save(pt);

        result.put("success", true);

        return result;
    }

    private void saveWareHouseProduct(Product product, double amount) {
        Warehouse warehouse = getWarehouse();
        WarehouseProduct wp = new WarehouseProduct(warehouse, product, amount);
        if (warehouseProductService.compare(wp)) {
            WarehouseProduct warehouseProduct = warehouseProductService.getWarehouse(wp);
            double totalAmount = warehouseProduct.getAmount() + amount;
            warehouseProduct.setAmount(totalAmount);
            warehouseProductService.save(warehouseProduct);
        } else {
            warehouseProductService.save(wp);
        }


    }

    @RequestMapping(value = "/get/suppliers/products", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
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


    @RequestMapping(value = "/get/sale/products", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public HashMap<String, Object> getSaleProducts() {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        List<Object> productList = new ArrayList<>();
        List<WarehouseProduct> warehouseProducts = warehouseProductService.findAll();
        for (WarehouseProduct wp : warehouseProducts) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", wp.getProduct().getId());
            map.put("name", wp.getProduct().getName());
            map.put("measuringUnit", wp.getProduct().getMeasuringUnit().getSymbol());
            map.put("amount", wp.getAmount());
            productList.add(map);
        }
        resultMap.put("res", productList);
        return resultMap;

    }

    @RequestMapping(value = "/get/all/suppliers", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public HashMap<String, Object> getSuppliers() {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        List<Object> suppliersList = new ArrayList<>();
        List<Agent> agents = agentService.findByType("S");
        for (Agent agent : agents) {
            HashMap<String, Object> map;
            map = new HashMap<String, Object>();
            map.put("id", agent.getId());
            map.put("businessName", agent.getBusinessName());
            suppliersList.add(map);
        }
        resultMap.put("result", suppliersList);
        return resultMap;

    }

    @GetMapping("/sales/create")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public String createSaleView(Model model) {
        return "sale/form";

    }


    @RequestMapping(value = "/get/all/customers", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public HashMap<String, Object> getCstomers() {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        List<Object> suppliersList = new ArrayList<>();
        List<Agent> agents = agentService.findByType("C");
        for (Agent agent : agents) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", agent.getId());
            map.put("businessName", agent.getBusinessName());
            suppliersList.add(map);
        }
        resultMap.put("result", suppliersList);
        return resultMap;

    }

    @RequestMapping(value = "/get/supplies/", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public HashMap<String, Object> getSupplies() {
        List<Object> ptList = new ArrayList<>();
        List<Object> result = new ArrayList<>();
        HashMap resMap = new HashMap();
        List<Object> invoiceNumbers = new ArrayList<>();
        ptList.add("Produkt");
        List<ProductTransaction> ptus = supplyService.findByType("F",getWarehouse());
        for (ProductTransaction pt : ptus) {
            ptList.add(pt.getInvoiceNumber());
        }
        result.add(ptList);
        for (ProductTransaction pt : ptus) {
            List<Object> ptusList = new ArrayList<>();
            for (ProductTransactionUnit ptu : pt.getProductTransactionUnits()) {
                ptusList.add(ptu.getProduct().getName());
                ptusList.add(ptu.getProduct().getAmount());
            }
            result.add(ptusList);
        }
        resMap.put("result", result);
        return resMap;
    }


    @RequestMapping(value = "/sales/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Secured({"ROLE_USER", "ROLE_ADMIN"}
    )
    public JSONObject saveSale(@RequestBody String requestobject) throws
            IOException, ParseException, java.text.ParseException {
        JSONObject result = new JSONObject();
        ProductTransaction pt = new ProductTransaction();
        pt.setType("Sh");
        pt.setWarehouse(getWarehouse());
        List<ProductTransactionUnit> productTransactionUnits = new ArrayList<ProductTransactionUnit>();
        JSONObject jsonObject = new JSONObject(requestobject);
        Product product = null;
        double amount = 0;
        double availableAmount = 0;
        Set<String> keyList = jsonObject.keySet();
        Map<String, Object> jsonMap = jsonObject.toMap();
        String date = (String) jsonMap.get("date");
        Object invoiceNumber = jsonMap.get("invoiceNumber");
        Object customer = jsonMap.get("customer");
        HashMap mp = (HashMap) customer;
        String str = mp.get("id").toString();
        int id = Integer.parseInt(str);
        Agent agent = agentService.findById(id);
        SimpleDateFormat dateFormaterr = new SimpleDateFormat("yyyy-MM-dd");
        Date dateValue = dateFormaterr.parse(date);
        pt.setDate(dateValue);
        pt.setAgent(agent);
        pt.setInvoiceNumber((String) invoiceNumber);
        supplyService.save(pt);
        Object ptus = jsonObject.get("ptus");
        List<Object> jsonArray = new JSONArray(ptus.toString()).toList();
        for (int index = 0; index < jsonArray.size(); index++) {
            HashMap<String, Object> map = (HashMap<String, Object>) jsonArray.get(index);
            ProductTransactionUnit ptu = new ProductTransactionUnit();
            product = productService.findById(Integer.parseInt(map.get("productId").toString()));
            amount = Double.parseDouble(map.get("amount").toString());
            availableAmount = Double.parseDouble(map.get("availableAmount").toString());
            ptu.setProduct(product);
            ptu.setAmount(amount);
            ptu.setPrice(Double.parseDouble(map.get("price").toString()));
            ptu.setProductTransaction(pt);
            supplyUnitService.save(ptu);
            updateWareHouseProduct(product, amount, availableAmount);
            productTransactionUnits.add(ptu);
            pt.setProductTransactionUnits(productTransactionUnits);

        }

        result.put("success", true);

        return result;
    }

    private void updateWareHouseProduct(Product product, double amount, double avilableAmount) {
        Warehouse warehouse = getWarehouse();
        WarehouseProduct wp = new WarehouseProduct(warehouse, product, amount);
        if (warehouseProductService.compare(wp)) {
            WarehouseProduct warehouseProduct = warehouseProductService.getWarehouse(wp);
            double totalAmount = warehouseProduct.getAmount() - amount;
            if (totalAmount < 0) {
                //raise exception
            }
            warehouseProduct.setAmount(totalAmount);
            warehouseProductService.save(warehouseProduct);
        } else {
            warehouseProductService.save(wp);
        }


    }


    @PostMapping("/supplies/{supplyId}/delete")
    public String delete(@PathVariable int supplyId, RedirectAttributes redirectAttributes) {
        ProductTransaction pt = supplyService.findById(supplyId);
//        if (pt.getProductTransactionUnits().size() > 1) {
//            redirectAttributes.addFlashAttribute("flash", "Vetem kategorite pa produkte mund te fshihen!");
//            return "redirect:/supplies";
//        }
        supplyService.delete(pt); //ka 0 produkte
        redirectAttributes.addFlashAttribute("flash", "Furnizimi u fshi! Sasia u shtua ne magazine");
        if (pt.getType().equalsIgnoreCase("F")) {
            return "redirect:/supplies";
        } else {
            return "redirect:/sales";
        }

    }

    @GetMapping("/top/products")
    public String getTopProducts(Model model) {
        return "warehouse_product/graphics";
    }


    @GetMapping("/get/top/products")
    public String getTopProductsView(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "product/index";
    }

}
