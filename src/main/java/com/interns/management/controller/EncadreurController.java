package com.interns.management.controller;

import com.interns.management.model.Admin;
import com.interns.management.model.Encadreur;
import com.interns.management.service.EncadreurService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Controller
public class EncadreurController {

    private final EncadreurService encadreurService;

    public EncadreurController(EncadreurService encadreurService) {
        this.encadreurService = encadreurService;
    }

    @GetMapping("/affichlisteencadreur")
    public String list(Model model) throws SQLException {
        model.addAttribute("list_encadreur", encadreurService.findAll());
        return "liste_des_encadreur";
    }

    @GetMapping("/afficherencadreurprofile")
    public String profile(@RequestParam("q") int id, Model model) throws SQLException {
        var view = encadreurService.findProfile(id);
        model.addAttribute("encadreur", view.encadreur());
        view.suivi().ifPresent(s -> model.addAttribute("suivi", s));
        return "encadreur_profil";
    }

    @GetMapping("/ajouterencadreur")
    public String addForm(HttpSession session, Model model) throws SQLException {
        Admin admin = (Admin) session.getAttribute("user");
        var form = encadreurService.loadAddForm(admin);
        model.addAttribute("direction", form.direction());
        model.addAttribute("categorie", form.categorie());
        return "ajouter_encadreur";
    }

    @PostMapping("/ajouterencadreur")
    public String add(@RequestParam String nom, @RequestParam String prenom,
                      @RequestParam String email, @RequestParam int categorie,
                      @RequestParam String direction) throws SQLException {
        Encadreur e = new Encadreur();
        e.setnom(nom);
        e.setprenom(prenom);
        e.setemail(email);
        e.setdirection(direction);
        encadreurService.create(e, categorie);
        return "redirect:/affichlisteencadreur";
    }

    @GetMapping("/modiferEncadreur")
    public String editForm(@RequestParam("q") int id, HttpSession session, Model model) throws SQLException {
        Admin admin = (Admin) session.getAttribute("user");
        var form = encadreurService.loadEditForm(id, admin);
        model.addAttribute("encadreur", form.encadreur());
        model.addAttribute("categorie", form.categorie());
        model.addAttribute("direction", form.direction());
        return "Modifier_encadreur";
    }

    @PostMapping("/modiferEncadreur")
    public String edit(@RequestParam("q") int id, @RequestParam String nom, @RequestParam String prenom,
                       @RequestParam String email) throws SQLException {
        Encadreur e = new Encadreur();
        e.setid(id);
        e.setnom(nom);
        e.setprenom(prenom);
        e.setemail(email);
        encadreurService.update(e);
        return "redirect:/afficherencadreurprofile?q=" + id;
    }

    @GetMapping("/suprimerEncadreur")
    public String delete(@RequestParam("q") int id) throws SQLException {
        encadreurService.delete(id);
        return "redirect:/affichlisteencadreur";
    }
}
