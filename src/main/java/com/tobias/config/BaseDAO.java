package com.tobias.config;

import java.sql.Connection;

public abstract class BaseDAO {

    protected Connection getConnection() {
        return DatabaseConfig.connect();
    }
}
