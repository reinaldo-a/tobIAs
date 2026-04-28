package com.tobias.controller.disciplines;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({
    "/Disciplines"
})

public class DisciplinesController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        
        String action = request.getServletPath();

        switch(action){
            case "/Disciplines":
                showDisciplines(request, response);
                return;
            default:
                response.sendRedirect(request.getContextPath() + "/404");
                return;
        }

    }

    protected void showDisciplines(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        request.setAttribute("pageHeading", "Disciplinas");
        request.setAttribute("pageTitle", "Gestão Acadêmica");

        request.setAttribute("contentPage", "/WEB-INF/templates/disciplines/disciplines.jsp");

        request.setAttribute("pageCss", "/assets/css/disciplines.css");

        request.getRequestDispatcher("/WEB-INF/templates/layout/base.jsp").forward(request, response);
    }
}
