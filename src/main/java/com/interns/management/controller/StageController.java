package com.interns.management.controller;

import com.interns.management.model.Admin;
import com.interns.management.service.StageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Controller
public class StageController {

    private final StageService stageService;

    public StageController(StageService stageService) {
        this.stageService = stageService;
    }

    @GetMapping("/affichlistestage")
    public String list(Model model) throws SQLException {
        model.addAttribute("stagelist", stageService.findAll());
        return "liste_des_stages";
    }

    @GetMapping("/afficherprofile")
    public String profile(@RequestParam("q") int id, Model model) throws SQLException {
        model.addAttribute("profile_stage", stageService.findById(id));
        return "stage_profil";
    }

    @GetMapping("/valider")
    public String validate(@RequestParam("q") String id) throws SQLException {
        stageService.validate(id);
        return "redirect:/afficherprofile?q=" + id;
    }

    @GetMapping("/refuser")
    public String refuse(@RequestParam("q") String id) throws SQLException {
        stageService.refuse(id);
        return "redirect:/afficherprofile?q=" + id;
    }

    @GetMapping("/proposer")
    public String proposeForm(Model model) throws SQLException {
        var form = stageService.loadProposeForm();
        model.addAttribute("categorie", form.categories());
        model.addAttribute("niveau", form.niveaux());
        return "proposertheme";
    }

    @PostMapping("/proposer")
    public String propose(@RequestParam String titre, @RequestParam int categorie,
                          @RequestParam int niveau, @RequestParam String Description,
                          Model model) throws SQLException {
        stageService.propose(titre, categorie, niveau, Description);
        model.addAttribute("proposer", true);
        var form = stageService.loadProposeForm();
        model.addAttribute("categorie", form.categories());
        model.addAttribute("niveau", form.niveaux());
        return "proposertheme";
    }

    @GetMapping("/modifier")
    public String editForm(@RequestParam("q") int id, Model model) throws SQLException {
        var form = stageService.loadEditForm(id);
        model.addAttribute("profile_stage", form.stage());
        model.addAttribute("categorie", form.categories());
        model.addAttribute("niveau", form.niveaux());
        return "modifer_stage";
    }

    @PostMapping("/Sauvgarder")
    public String save(@RequestParam("q") int id, @RequestParam String titre,
                       @RequestParam int niveau, @RequestParam String Description) throws SQLException {
        stageService.save(id, titre, niveau, Description);
        return "redirect:/afficherprofile?q=" + id;
    }

    @GetMapping("/affecter")
    public String affectForm(HttpSession session, Model model) throws SQLException {
        Admin user = (Admin) session.getAttribute("user");
        var data = stageService.loadAffecterData(user);
        model.addAttribute("stagelist", data.stages());
        model.addAttribute("list_satgiare", data.stagiares());
        model.addAttribute("list_encadreur", data.encadreurs());
        return "affecter_stage";
    }

    @PostMapping("/affecter")
    public String affect(@RequestParam int stage, @RequestParam("stagiares") String[] stagiares,
                         @RequestParam int encadreur, @RequestParam String debut, @RequestParam String fin,
                         HttpSession session, Model model) throws SQLException {
        stageService.affect(stage, stagiares, encadreur, debut, fin);
        Admin user = (Admin) session.getAttribute("user");
        var data = stageService.loadAffecterData(user);
        model.addAttribute("stagelist", data.stages());
        model.addAttribute("list_satgiare", data.stagiares());
        model.addAttribute("list_encadreur", data.encadreurs());
        model.addAttribute("done", true);
        return "affecter_stage";
    }
}
