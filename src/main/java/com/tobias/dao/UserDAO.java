package com.tobias.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.tobias.model.User;

public class UserDAO extends BaseDAO {

    public void inserirUsuario(User user) throws SQLException {
        String sql = "INSERT INTO usuario (nome, cpf, email, senha) VALUES (?, ?, ?, ?)";

        try (
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, user.getName());
            stmt.setLong(2, user.getCpf());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());

            stmt.executeUpdate();
        }
    }
}