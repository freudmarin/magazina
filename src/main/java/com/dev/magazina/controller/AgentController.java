package com.dev.magazina.controller;

import com.dev.magazina.model.Agent;
import com.dev.magazina.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AgentController {
    @Autowired
    private AgentService agentService;

    @GetMapping("/suppliers")
    public String index(Model model) {
        List<Agent> agents = agentService.findAll();
        model.addAttribute("suppliers", agents);
        return "agent/index";
    }


    @GetMapping("/suppliers/create")
    public String create(Model model) {
        if (!model.containsAttribute("supplier")) {
            model.addAttribute("supplier", new Agent());
        }
        return "agent/supplier_form";
    }

    @PostMapping("/suppliers/create")
    public String store(@Valid Agent supplier, BindingResult result,
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.agent", result);
            redirectAttributes.addFlashAttribute("supplier", supplier);
            return "redirect:/suppliers/create";
        }

        redirectAttributes.addFlashAttribute("flash", "Furnitori u shtua me sukses");
        supplier.setType("S");
        agentService.save(supplier);
        return "redirect:/suppliers";
    }


}
