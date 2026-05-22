package com.interns.management.controller;

import com.interns.management.model.Admin;
import com.interns.management.model.Stagiare;
import com.interns.management.service.StagiareService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Controller
public class StagiareController {

    private final StagiareService stagiareService;

    public StagiareController(StagiareService stagiareService) {
        this.stagiareService = stagiareService;
    }

    @GetMapping({"/affichlistestagiare", "/affichlistestagiare/"})
    public String list(Model model) throws SQLException {
        model.addAttribute("list_satgiare", stagiareService.findAll());
        return "liste_des_stagiares";
    }

    @GetMapping("/afficherstagiareprofile")
    public String profile(@RequestParam("q") int id, Model model) throws SQLException {
        model.addAttribute("stagiare", stagiareService.findById(id));
        return "Stagiare_profil";
    }

    @GetMapping("/ajouterstagire")
    public String addForm(HttpSession session, Model model) throws SQLException {
        Admin admin = (Admin) session.getAttribute("user");
        var form = stagiareService.loadAddForm(admin);
        model.addAttribute("direction", form.direction());
        model.addAttribute("categorie", form.categorie());
        model.addAttribute("niveau", form.niveaux());
        return "ajouter_stagiare";
    }

    @PostMapping("/ajouterstagire")
    public String add(@RequestParam String nom, @RequestParam String prenom,
                      @RequestParam String email, @RequestParam int categorie,
                      @RequestParam int niveau, @RequestParam int direction,
                      @RequestParam String etabalissement) throws SQLException {
        Stagiare s = new Stagiare();
        s.setnom(nom);
        s.setprenom(prenom);
        s.setemail(email);
        s.setetabalissement(etabalissement);
        stagiareService.create(s, categorie, niveau, direction);
        return "redirect:/affichlistestagiare";
    }

    @GetMapping("/modiferStagire")
    public String editForm(@RequestParam("q") int id, HttpSession session, Model model) throws SQLException {
        Admin admin = (Admin) session.getAttribute("user");
        var form = stagiareService.loadEditForm(id, admin);
        model.addAttribute("stagiare", form.stagiare());
        model.addAttribute("categorie", form.categorie());
        model.addAttribute("direction", form.direction());
        model.addAttribute("niveau", form.niveaux());
        return "Modifier_stagiare";
    }

    @PostMapping("/modiferStagire")
    public String edit(@RequestParam("q") int id, @RequestParam String nom, @RequestParam String prenom,
                       @RequestParam String email, @RequestParam int niveau, @RequestParam String direction,
                       @RequestParam String etabalissement) throws SQLException {
        Stagiare s = new Stagiare();
        s.setid(id);
        s.setnom(nom);
        s.setprenom(prenom);
        s.setemail(email);
        s.setdirection(direction);
        s.setetabalissement(etabalissement);
        stagiareService.update(s, niveau);
        return "redirect:/afficherstagiareprofile?q=" + id;
    }

    @GetMapping("/suprimerStagiare")
    public String delete(@RequestParam("q") int id) throws SQLException {
        stagiareService.delete(id);
        return "redirect:/affichlistestagiare";
    }
}
