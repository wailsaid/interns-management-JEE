package com.interns.management.controller;

import com.interns.management.service.ReferenceDataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Controller
public class ReferenceDataController {

    private final ReferenceDataService referenceDataService;

    public ReferenceDataController(ReferenceDataService referenceDataService) {
        this.referenceDataService = referenceDataService;
    }

    @GetMapping("/listcategoories")
    public String categories(Model model) throws SQLException {
        model.addAttribute("Categories", referenceDataService.findCategories());
        return "listCategorie";
    }

    @PostMapping("/listcategoories")
    public String addCategory(@RequestParam String nom) throws SQLException {
        referenceDataService.addCategory(nom);
        return "redirect:/listcategoories";
    }

    @GetMapping("/supcat")
    public String deleteCategory(@RequestParam("q") int id) throws SQLException {
        referenceDataService.deleteCategory(id);
        return "redirect:/listcategoories";
    }

    @GetMapping("/listdirections")
    public String directions(Model model) throws SQLException {
        model.addAttribute("directions", referenceDataService.findDirections());
        return "listdirection";
    }

    @PostMapping("/listdirections")
    public String addDirection(@RequestParam String nom) throws SQLException {
        referenceDataService.addDirection(nom);
        return "redirect:/listdirections";
    }

    @GetMapping("/supdir")
    public String deleteDirection(@RequestParam("q") int id) throws SQLException {
        referenceDataService.deleteDirection(id);
        return "redirect:/listdirections";
    }
}
