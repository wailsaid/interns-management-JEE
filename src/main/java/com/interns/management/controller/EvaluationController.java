package com.interns.management.controller;

import com.interns.management.model.Admin;
import com.interns.management.service.EvaluationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;

@Controller
public class EvaluationController {

    private final EvaluationService evaluationService;

    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @GetMapping("/evaluer")
    public String list(HttpSession session, Model model) throws SQLException {
        Admin user = (Admin) session.getAttribute("user");
        model.addAttribute("stages", evaluationService.findStagesForEvaluation(user.getcategorie()));
        return "evaluation";
    }

    @GetMapping("/getstagiares")
    public String modal(@RequestParam("q") int id, Model model) throws SQLException {
        var data = evaluationService.loadModalData(id);
        model.addAttribute("encadreur", data.encadreur());
        model.addAttribute("date", data.dates());
        model.addAttribute("list", data.stagiares());
        return "page_evaluation";
    }

    @PostMapping("/evaluer")
    public String submit(@RequestParam("stagiares") String[] stagiares,
                         @RequestParam("stage") String[] stage,
                         @RequestParam String evaluation,
                         @RequestParam("travail") MultipartFile travail,
                         HttpSession session, Model model) throws Exception {
        evaluationService.submitEvaluation(stagiares, stage, evaluation, travail);
        Admin user = (Admin) session.getAttribute("user");
        model.addAttribute("stages", evaluationService.findStagesForEvaluation(user.getcategorie()));
        return "evaluation";
    }
}
