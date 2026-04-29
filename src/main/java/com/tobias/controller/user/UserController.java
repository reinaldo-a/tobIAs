    package com.tobias.controller.user;

import java.io.IOException;

import org.postgresql.util.PasswordUtil;

import com.tobias.config.PasswordHash;
import com.tobias.dao.UserDAO;
import com.tobias.model.User;

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
                    response.sendRedirect(request.getContextPath() + "/404");
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
                response.sendRedirect(request.getContextPath() + "/404");
                return;
        }
    }
    
    protected void ShowRegidterFormes(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        request.getRequestDispatcher("/WEB-INF/templates/user/register.jsp").forward(request, response);

    }

    protected void registerUser(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String nome = request.getParameter("nome");
        String cpfText = request.getParameter("cpf");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        try {
            long cpf = Long.parseLong(cpfText.replaceAll("\\D", ""));

            // AQUI você gera o hash
            String senhaHash = PasswordHash.hashPassword(senha);

            // Agora o usuário recebe o hash, não a senha pura
            User user = new User(nome, cpf, email, senhaHash, 0);

            UserDAO userDAO = new UserDAO();

            if (userDAO.inserirUsuario(user)) {
                response.sendRedirect(request.getContextPath() + "/dashboard");
                return;
            }

        } catch (Exception e) {
            System.out.println("Erro ao cadastrar usuario: " + e.getMessage());
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/user/register-form");
    }
}   
