package com.tobias.dao;

import com.tobias.model.Material;
import com.tobias.model.Disciplina;
import java.sql.*;
import java.util.*;

public class MaterialDAO {
    private Connection conn;

    public MaterialDAO(Connection conn) {
        this.conn = conn;
    }

    public void criar(Material material) throws SQLException {
        String sql = "INSERT INTO material (titulo, conteudo, disciplina_id) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, material.getTitulo());
        stmt.setString(2, material.getConteudo());
        stmt.setInt(3, material.getDisciplina().getId());
        stmt.executeUpdate();
    }

    public void editar(Material material) throws SQLException {
        String sql = "UPDATE material SET titulo=?, conteudo=? WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, material.getTitulo());
        stmt.setString(2, material.getConteudo());
        stmt.setInt(3, material.getId());
        stmt.executeUpdate();
    }

    public void remover(int id) throws SQLException {
        String sql = "DELETE FROM material WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    public List<Material> listarPorDisciplina(Disciplina disciplina) throws SQLException {
        List<Material> materiais = new ArrayList<>();
        String sql = "SELECT * FROM material WHERE disciplina_id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, disciplina.getId());
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Material m = new Material(
                rs.getInt("id"),
                rs.getString("titulo"),
                rs.getString("conteudo"),
                disciplina
            );
            materiais.add(m);
        }
        return materiais;
    }
}
