package com.tobias.controller.user;

import java.io.IOException;

import com.tobias.application.FlashMessage;
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
                showRegisterForm(request, response);
                return;

            case "/user/update-form":
                // TODO
                return;

            case "/user/read":
                // TODO
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

            default:
                response.sendRedirect(request.getContextPath() + "/404");
                return;
        }
    }

    // ============================
    // VIEW
    // ============================

    private void showRegisterForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        FlashMessage.get(request);

        request.getRequestDispatcher("/WEB-INF/templates/user/register.jsp")
                .forward(request, response);
    }

    // ============================
    // ACTION
    // ============================

    private void registerUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        request.setCharacterEncoding("UTF-8");

        String nome = request.getParameter("nome");
        String cpfText = request.getParameter("cpf");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        try {
            long cpf = Long.parseLong(cpfText.replaceAll("\\D", ""));
            String senhaHash = PasswordHash.hashPassword(senha);

            User user = new User(nome, cpf, email, senhaHash, 0);

            UserDAO userDAO = new UserDAO();
            userDAO.inserirUsuario(user); // AGORA lança exceção

            FlashMessage.set(request, "success", "Usuário cadastrado com sucesso!");
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;

        } catch (Exception e) {
            e.printStackTrace();

            String msg = "Falha ao cadastrar usuário";

            // Se quiser tratar erro de duplicidade (melhor depois)
            if (e.getMessage() != null && e.getMessage().toLowerCase().contains("duplicate")) {
                msg = "CPF ou e-mail já cadastrado";
            }

            FlashMessage.set(request, "danger", msg);
            response.sendRedirect(request.getContextPath() + "/user/register-form");
        }
    }
}