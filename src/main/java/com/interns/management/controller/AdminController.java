package com.interns.management.controller;

import com.interns.management.model.Admin;
import com.interns.management.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Controller
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/listadmins")
    public String list(Model model) throws SQLException {
        model.addAttribute("listadmin", adminService.findAll());
        return "listadmin";
    }

    @GetMapping("/afficheradminprofile")
    public String profile(@RequestParam("q") String id, Model model) throws SQLException {
        model.addAttribute("admin", adminService.findById(id));
        return "admin_profil";
    }

    @GetMapping("/ajouteradmin")
    public String addForm(Model model) throws SQLException {
        var form = adminService.loadAddForm();
        model.addAttribute("categorie", form.categories());
        model.addAttribute("direction", form.directions());
        return "ajouteradmin";
    }

    @PostMapping("/ajouteradmin")
    public String add(@RequestParam String nom, @RequestParam String prenom,
                      @RequestParam String username, @RequestParam String password,
                      @RequestParam String direction, @RequestParam String categorie) throws SQLException {
        Admin admin = new Admin();
        admin.setnom(nom);
        admin.setprenom(prenom);
        admin.setusername(username);
        admin.setpassword(password);
        admin.setdirection(direction);
        admin.setcategorie(categorie);
        adminService.create(admin);
        return "redirect:/listadmins";
    }

    @GetMapping("/modiferAdmin")
    public String editForm(@RequestParam("q") int id, Model model) throws SQLException {
        var form = adminService.loadEditForm(id);
        model.addAttribute("admin", form.admin());
        model.addAttribute("categorie", form.categories());
        model.addAttribute("direction", form.directions());
        return "modifieradmin";
    }

    @PostMapping("/modiferAdmin")
    public String edit(@RequestParam("q") int id, @RequestParam String nom, @RequestParam String prenom,
                       @RequestParam String username, @RequestParam String password,
                       @RequestParam String direction, @RequestParam String categorie) throws SQLException {
        Admin admin = new Admin();
        admin.setid(id);
        admin.setnom(nom);
        admin.setprenom(prenom);
        admin.setusername(username);
        admin.setpassword(password);
        admin.setdirection(direction);
        admin.setcategorie(categorie);
        adminService.update(admin);
        return "redirect:/afficheradminprofile?q=" + id;
    }

    @GetMapping("/suprimerAdmin")
    public String delete(@RequestParam("q") String id) throws SQLException {
        adminService.delete(id);
        return "redirect:/listadmins";
    }
}
