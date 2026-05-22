package com.interns.management.controller;

import com.interns.management.model.Admin;
import com.interns.management.model.Employee;
import com.interns.management.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/Authentifier")
    public String loginForm() {
        return "Authentification";
    }

    @PostMapping("/Authentifier")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) throws SQLException {
        var admin = authService.authenticate(username, password);
        if (admin.isPresent()) {
            if ("admin".equals(username) && "admin".equals(password)) {
                return "redirect:/appadmin";
            }
            session.setAttribute("user", admin.get());
            return "redirect:/page_d_acceil";
        }
        model.addAttribute("erreur", true);
        return "Authentification";
    }

    @GetMapping("/appadmin")
    public String appAdmin(HttpSession session) {
        Admin admin = new Admin();
        admin.setusername("admin");
        admin.setcategorie("admin");
        session.setAttribute("user", admin);
        return "redirect:/page_d_acceil";
    }

    @GetMapping("/employee")
    public String employeeAccess(HttpSession session) {
        Employee user = new Employee();
        user.setusername("employe");
        session.setAttribute("user", user);
        return "redirect:/page_d_acceil";
    }

    @GetMapping("/deconnecter")
    public String logout(HttpSession session) {
        if (session != null) {
            session.removeAttribute("user");
            session.invalidate();
        }
        return "redirect:/Authentifier";
    }
}
