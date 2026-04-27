package com.tobias.dao;

import com.tobias.config.DatabaseConfig;
import java.sql.Connection;

public abstract class BaseDAO {

    protected Connection getConnection() {
        return DatabaseConfig.connect();
    }
}
