package com.tobias.controller.auth;

import java.io.IOException;

import com.tobias.config.PasswordHash;
import com.tobias.dao.AuthDAO;
import com.tobias.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({"/login", "/logout"})
public class AuthController extends HttpServlet {

    // Controla as requisições GET de autenticação: exibe a tela de login ou faz logout.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        // Encerra a sessão atual e redireciona o usuário para a página de login.
        if ("/logout".equals(action)) {
            var session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Quando a rota for /login, apenas mostra o formulário de autenticação.
        request.getRequestDispatcher("/WEB-INF/templates/auth/login.jsp")
                .forward(request, response);
    }

    // Controla o envio do formulário de login.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Garante que e-mail e senha enviados pelo formulário sejam lidos em UTF-8.
        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        // Busca o usuário pelo e-mail informado para comparar a senha digitada.
        AuthDAO dao = new AuthDAO();
        User user = dao.buscarPorEmail(email);

        boolean senhaValida = false;

        if (user != null) {
            String senhaSalva = user.getPassword();

            // Aceita senhas com hash BCrypt e também senhas antigas salvas em texto puro.
            if (PasswordHash.isBcryptHash(senhaSalva)) {
                senhaValida = PasswordHash.checkPassword(senha, senhaSalva);
            } else if (senhaSalva != null && senhaSalva.equals(senha)) {
                senhaValida = true;
            }
        }

        if (senhaValida) {
            // Login válido: cria a sessão com os dados básicos do usuário.
            var session = request.getSession();
            session.setAttribute("userId", user.getId());
            session.setAttribute("userName", user.getName());
            session.setAttribute("userEmail", user.getEmail());
            session.setAttribute("usuarioLogado", user);

            response.sendRedirect(request.getContextPath() + "/dashboard");
        } else {
            // Login inválido: retorna para o formulário mostrando a mensagem de erro.
            request.setAttribute("erro", "Credenciais inválidas");
            request.getRequestDispatcher("/WEB-INF/templates/auth/login.jsp").forward(request, response);
        }
    }
}
