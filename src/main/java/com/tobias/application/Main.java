package com.tobias.application;

import com.tobias.dao.DatabaseHealthDAO;

public class Main {
    public static void main(String[]args){
        System.out.println("sistema iniciado!");

        DatabaseHealthDAO databaseHealthDAO = new DatabaseHealthDAO();

        if (databaseHealthDAO.isDatabaseAvailable()) {
            System.out.println("Banco disponivel!");
        }
    }
}
