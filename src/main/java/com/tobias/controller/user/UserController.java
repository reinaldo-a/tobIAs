package com.tobias.controller.user;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({
    "/user/register-form",
    "/user/register-save",
    "/user/update-form",
    "/user/update-save",
    "/user/read",
    "/user/delete"
})
public class UserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getServletPath();

        switch (action) {
            case "/user/register-form":
                ShowRegidterFormes(request, response);
                return;
            case "/user/update-form":
                
                return;
            case "/user/read":
                    
                return;
            default:
                response.sendRedirect(request.getContextPath() + "/dashboard");
                return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        
        switch (action) {
            case "/user/register-save":
                registerUser(request, response);
                return;
            case "/user/read":
                
                return;
            case "/user/update":
                
                return;
            case "/user/delete":
                
                return;
            default:
                response.sendRedirect(request.getContextPath() + "/dashboard");
                return;

        response.sendRedirect(request.getContextPath() + "/login");
    }
    
    protected void ShowRegidterFormes(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        request.getRequestDispatcher("/WEB-INF/templates/user/register.jsp").forward(request, response);

    }

    protected void registerUser(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        response.sendRedirect(request.getContextPath() + "/dashboard");
        return;
    }
}   
