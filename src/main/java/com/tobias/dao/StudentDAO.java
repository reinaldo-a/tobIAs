package com.tobias.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement; 

public class StudentDAO extends BaseDAO{

    public int getOrCreateStudent(int id){
        int alunoId = -1;
        String get = "SELECT id FROM aluno WHERE usuario_id = ?";
        try{
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement(get);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                int alunoIdEncontrado = rs.getInt("id");
                return alunoIdEncontrado;
            }
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }
        String insert = "INSERT INTO aluno (usuario_id) VALUES (?)";
        try{
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement(insert,Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1,id);
            pst.executeUpdate();
            ResultSet rsKey = pst.getGeneratedKeys();
            if(rsKey.next()){
                alunoId = rsKey.getInt(1);
            }
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }
        return alunoId;
    }
}
