package com.tobias.controller.dashboard;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/", "/dashboard"})
public class DashboardController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String contextPath = request.getContextPath();
        String path = request.getRequestURI().substring(contextPath.length());

        if (!"/".equals(path) && !"/dashboard".equals(path)) {
            response.sendRedirect(contextPath + "/404");
            return;
        }

        request.setAttribute("contentPage", "/WEB-INF/templates/dashboard/dashboard.jsp");
        request.setAttribute("pageCss", "/assets/css/dashboard.css");
        request.setAttribute("pageJs", "/assets/js/dashboard.js");
        request.getRequestDispatcher("/WEB-INF/templates/layout/base.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.sendRedirect(request.getContextPath() + "/404");
    }
}
