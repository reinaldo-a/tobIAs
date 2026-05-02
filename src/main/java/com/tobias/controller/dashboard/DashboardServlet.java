
package com.tobias.controller.dashboard;

import java.io.IOException;

import com.tobias.application.FlashMessage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({"/", "/dashboard"})
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        request.setAttribute("pageTitle", "Dashboard - ToBIAS");
        request.setAttribute("pageSection", "Painel");
        request.setAttribute("pageHeading", "Dashboard");
        request.setAttribute("userName", "RJ");
        request.setAttribute("userRole", "Sistema");
        request.setAttribute("contentPage", "/WEB-INF/dashboard/dashboard.jsp");
        request.setAttribute("pageCss", "/assets/css/dashboard.css");
        request.setAttribute("pageJs", "/assets/js/dashboard.js");
        request.getRequestDispatcher("/WEB-INF/layout/base.jsp").forward(request, response);
    }

    //  método doPost
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        boolean sucesso = excluirRegistro(request.getParameter("id"));

        if (sucesso) {
            FlashMessage.set(request.getSession(), "success", "Registro excluído com sucesso!");
        } else {
            FlashMessage.set(request.getSession(), "error", "Erro ao excluir registro.");
        }

        response.sendRedirect("dashboard");
    }

    /**
     * Exclui um registro do sistema baseado no ID fornecido
     * 
     * @param id ID do registro a ser excluído
     * @return true se a exclusão foi bem-sucedida, false caso contrário
     */
    private boolean excluirRegistro(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }

        try {
            // TODO: Implementar a lógica de exclusão no banco de dados
            // Por enquanto, simula sucesso na exclusão
            long idLong = Long.parseLong(id);
            // Adicione aqui a chamada ao seu DAO ou serviço de exclusão
            return idLong > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
