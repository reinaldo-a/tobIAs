package com.tobias.application;

import com.tobias.config.DatabaseConfig;

public class Main {
    public static void main(String[]args){
        System.out.println("sistema iniciado!");

        DatabaseConfig.connect();
    }
}
