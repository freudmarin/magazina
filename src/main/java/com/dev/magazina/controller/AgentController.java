package com.dev.magazina.controller;

import com.dev.magazina.model.Agent;
import com.dev.magazina.service.AgentService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@Controller
public class AgentController {
    @Autowired
    private AgentService agentService;

    @GetMapping("/suppliers")
    public String index(Model model) {
        List<Agent> agents = agentService.findByType("S");
        model.addAttribute("suppliers", agents);
        return "agent/index";
    }


    //    @GetMapping("/get/suppliers")
    @RequestMapping(value = "/get/suppliers", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public HashMap<String, String> getSuppliers() {
        HashMap<String, String> resultMap = new HashMap<String, String>();
        List<Agent> agents = agentService.findByType("S");
        Gson gson = new Gson();
        String jsonList = gson.toJson(agents);

        resultMap.put("res", jsonList);
//        model.addAttribute("suppliers", agents);

        return resultMap;

    }


    @GetMapping("/suppliers/distribution")
    public String supplierDistribution(Model model) {
//        List<Agent> agents = agentService.findByType("S");
//        model.addAttribute("suppliers", agents);
        return "agent/supplier_distribution";
    }

    @GetMapping("/suppliers/create")
    public String create(Model model) {
        if (!model.containsAttribute("supplier")) {
            model.addAttribute("supplier", new Agent());
        }
        model.addAttribute("heading","Shto Furnitor");
        model.addAttribute("submit","Shto");
        model.addAttribute("action", "/suppliers/create");
        return "agent/supplier_form";
    }

    @PostMapping("/suppliers/create")
    public String store(@Valid Agent supplier, BindingResult result,
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.supplier", result);
            redirectAttributes.addFlashAttribute("supplier", supplier);
            return "redirect:/suppliers/create";
        }

        agentService.save(supplier);
        redirectAttributes.addFlashAttribute("flash", "Furnitori u shtua me sukses!");
        return "redirect:/suppliers";
    }

    @PostMapping("/suppliers/{supplierId}/delete")
    public String deleteCustomer(@PathVariable int supplierId, RedirectAttributes redirectAttributes) {
        Agent agent = agentService.findById(supplierId);
        agentService.delete(agent);
        redirectAttributes.addFlashAttribute("flash", "Furnitori u fshi!");
        return "redirect:/suppliers";
    }


    //Customer
    @GetMapping("/customers")
    public String getCustomers(Model model) {
        List<Agent> agents = agentService.findByType("C");
        model.addAttribute("customers", agents);
        return "agent/customer_index";
    }

    @GetMapping("/customers/create")
    public String createCustomers(Model model) {
        if (!model.containsAttribute("customer")) {
            model.addAttribute("customer", new Agent());
        }
        model.addAttribute("action", "/customers/create");
        model.addAttribute("heading","Shto Klientin");
        model.addAttribute("submit","Shto");
        return "agent/customer_form";
    }

    @PostMapping("/customers/create")
    public String storeCustomers(@Valid Agent customer, BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.agent", result);
            redirectAttributes.addFlashAttribute("customer", customer);
            return "redirect:/customers/create";
        }

        redirectAttributes.addFlashAttribute("flash", "Klienti u shtua me sukses");
        customer.setType("C");
        String lat = (customer.getLatitude().length() > 0) ? customer.getLatitude() : "";
        String lng = (customer.getLongitude().length() > 0) ? customer.getLongitude() : "";
        customer.setLongitude(lat);
        customer.setLatitude(lng);
        agentService.save(customer);
        return "redirect:/customers";
    }

    @PostMapping("/customers/{customerId}/delete")
    public String delete(@PathVariable int customerId, RedirectAttributes redirectAttributes) {
        Agent agent = agentService.findById(customerId);
        agentService.delete(agent);
        redirectAttributes.addFlashAttribute("flash", "Klienti u fshi!");
        return "redirect:/customers";
    }
    @GetMapping("/suppliers/{supplierId}/edit")
    public String editSupplier(@PathVariable int supplierId, Model model) {
        if (!model.containsAttribute("supplier")) {
            model.addAttribute("supplier", agentService.findById(supplierId));
        }
        model.addAttribute("action", "/suppliers/edit");
        model.addAttribute("heading","Modifiko Furnitorin");
        model.addAttribute("submit","Modifiko");
        return "agent/supplier_form";
}

    @PostMapping("/suppliers/edit")
    public String updateSupplier(@Valid Agent supplier, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.supplier", result);
            redirectAttributes.addFlashAttribute("supplier", supplier);
            return String.format("redirect:/suppliers/%s/edit", supplier.getId());
        }

        agentService.save(supplier);
        redirectAttributes.addFlashAttribute("flash", "Furnitori u modifikua me sukses!");
        return "redirect:/suppliers";
    }



    @GetMapping("/customers/{customerId}/edit")
    public String editCustomer(@PathVariable int customerId, Model model) {
        if (!model.containsAttribute("customer")) {
            model.addAttribute("customer", agentService.findById(customerId));
        }
        model.addAttribute("action", "/customers/edit");
        model.addAttribute("heading","Modifiko Klientin");
        model.addAttribute("submit","Modifiko");
        return "agent/customer_form";
    }

    @PostMapping("/customers/edit")
    public String updateCustomer(@Valid Agent customer, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.customer", result);
            redirectAttributes.addFlashAttribute("customer", customer);
            return String.format("redirect:/customers/%s/edit", customer.getId());
        }

        agentService.save(customer);
        redirectAttributes.addFlashAttribute("flash", "Klienti u modifikua me sukses!");
        return "redirect:/customers";
    }




}
