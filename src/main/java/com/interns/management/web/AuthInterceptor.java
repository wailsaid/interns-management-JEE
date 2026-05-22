package com.interns.management.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        HttpSession session = request.getSession(false);
        boolean connected = session != null && session.getAttribute("user") != null;
        String loginPath = request.getContextPath() + "/Authentifier";
        String employeePath = request.getContextPath() + "/employee";
        String rootPath = request.getContextPath() + "/";
        boolean publicPage = request.getRequestURI().equals(loginPath)
                || request.getRequestURI().equals(employeePath)
                || request.getRequestURI().equals(rootPath);
        boolean authPage = request.getRequestURI().endsWith("/Authentifier");

        if (connected && authPage) {
            response.sendRedirect(request.getContextPath() + "/page_d_acceil");
            return false;
        }
        if (connected || publicPage) {
            return true;
        }
        response.sendRedirect(request.getContextPath() + "/Authentifier");
        return false;
    }
}
