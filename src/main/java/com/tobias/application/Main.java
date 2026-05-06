package com.tobias.application;

import com.tobias.config.TestConnectDAO;

public class Main {
    public static void main(String[]args){
        System.out.println("sistema iniciado!");

        TestConnectDAO databaseHealthDAO = new TestConnectDAO();

        if (databaseHealthDAO.isDatabaseAvailable()) {
            System.out.println("Banco disponivel!");
        }
    }
}
