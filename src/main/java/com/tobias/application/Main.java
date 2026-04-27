package com.tobias.application;

import com.tobias.config.testConnectDAO;

public class Main {
    public static void main(String[]args){
        System.out.println("sistema iniciado!");

        testConnectDAO databaseHealthDAO = new testConnectDAO();

        if (databaseHealthDAO.isDatabaseAvailable()) {
            System.out.println("Banco disponivel!");
        }
    }
}
