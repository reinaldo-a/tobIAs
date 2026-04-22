package com.tobias.controller.dashboard;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/", "/dashboard"})
public class DashboardServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        
        request.setAttribute("pageTitle", "Dashboard - TobIAs");
        request.setAttribute("pageSection", "Painel");
        request.setAttribute("pageHeading", "Dashboard");
        request.setAttribute("userName", "RJ");
        request.setAttribute("userRole", "Sistema");
        request.setAttribute("contentPage", "/WEB-INF/dashboard/dashboard.jsp");
        request.setAttribute("pageCss", "/assets/css/dashboard.css");
        request.setAttribute("pageJs", "/assets/js/dashboard.js");
        request.getRequestDispatcher("/WEB-INF/layout/base.jsp").forward(request, response);
    }
}
