package com.tobias.controller.autenticacao;

import java.io.IOException;

import com.tobias.application.FlashMessage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String usuario = request.getParameter("usuario");
        String senha = request.getParameter("senha");

        if ("admin".equals(usuario) && "123".equals(senha)) {
            FlashMessage.set(request.getSession(), "success", "Login realizado com sucesso!");
            response.sendRedirect("dashboard");
        } else {
            FlashMessage.set(request.getSession(), "error", "Usuário ou senha inválidos.");
            response.sendRedirect("login");
        }
    }
}
