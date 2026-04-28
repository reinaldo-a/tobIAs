package com.tobias.config;

import java.sql.Connection;
import java.sql.SQLException;

public class testConnectDAO extends BaseDAO {

    public boolean isDatabaseAvailable() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.out.println("Erro ao validar conexao com o banco:");
            e.printStackTrace();
            return false;
        }
    }
}
