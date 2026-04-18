package application;

import util.DatabaseConfig;
public class Main {
    public static void main(String[]args){
        System.out.println("sistema iniciado!");

        DatabaseConfig.connect();
    }
}
