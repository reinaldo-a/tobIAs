package com.tobias;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConfig {

    public static Connection connect() {
        try {
            // carrega o .env
            Dotenv dotenv = Dotenv.load();

            String host = dotenv.get("DB_HOST");
            String port = dotenv.get("DB_PORT");
            String db = dotenv.get("POSTGRES_DB");
            String user = dotenv.get("POSTGRES_USER");
            String password = dotenv.get("POSTGRES_PASSWORD");

            String url = "jdbc:postgresql://" + host + ":" + port + "/" + db;

            Connection conn = DriverManager.getConnection(url, user, password);

            System.out.println("Conectado no banco!");
            return conn;

        } catch (Exception e) {
            System.out.println("Erro ao conectar no banco:");
            e.printStackTrace();
            return null;
        }
    }
}