package com.tobias.controller.material;

import java.io.IOException;
import java.sql.Connection;

import com.tobias.dao.MaterialDAO;
import com.tobias.model.Disciplina;
import com.tobias.model.Material;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MaterialController extends HttpServlet {
    private MaterialDAO materialDAO;

    @Override
    public void init() {
        Connection conn = (Connection) getServletContext().getAttribute("DBConnection");
        materialDAO = new MaterialDAO(conn);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        try {
            if ("criar".equals(action)) {
                int disciplinaId = Integer.parseInt(req.getParameter("disciplinaId"));
                Disciplina disciplina = new Disciplina();
                disciplina.setId(disciplinaId);
                Material material = new Material(0, req.getParameter("titulo"), req.getParameter("conteudo"), disciplina);
                materialDAO.criar(material);
            } else if ("editar".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                Material material = new Material(id, req.getParameter("titulo"), req.getParameter("conteudo"), null);
                materialDAO.editar(material);
            } else if ("remover".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                materialDAO.remover(id);
            }
            resp.sendRedirect("disciplinas.jsp");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
