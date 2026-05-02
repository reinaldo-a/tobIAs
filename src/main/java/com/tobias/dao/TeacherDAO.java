package com.tobias.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;  

public class TeacherDAO extends BaseDAO{

    public int getOrCreateTeacher(int idUser){
        int professorId = -1;

        //tenta achar um professor já existente
        String get = "SELECT id FROM professor WHERE usuario_id = ?";
        try{
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement(get);
            pst.setInt(1,idUser);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                int id = rs.getInt("id");
                return id;
            }
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }

        //caso não achar cria um professor com o id do usuario
        String insert = "INSERT INTO professor (usuario_id) VALUES (?)";
        try{
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1,idUser);
            pst.executeUpdate();

            ResultSet rsKeys = pst.getGeneratedKeys();
            if(rsKeys.next()){
                professorId = rsKeys.getInt(1);
            }
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }
        return professorId;
    }
}
