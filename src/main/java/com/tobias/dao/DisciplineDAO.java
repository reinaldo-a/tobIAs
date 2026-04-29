package com.tobias.dao;

import com.tobias.model.Discipline;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DisciplineDAO extends BaseDAO{

    public void save(Discipline discipline){

        String add = "INSERT INTO disciplina(nome, codigo, descricao) VALUES (?,?,?)";

        try{
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement(add);
            pst.setString(1,discipline.getName());
            pst.setString(2,discipline.getCode());
            pst.setString(3,discipline.getDescription());
            pst.executeUpdate();
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public List<Discipline> listDisciplines(){
        List<Discipline> lista = new ArrayList<>();
        String read = "SELECT * FROM disciplina";
        try{
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement(read);
            ResultSet rs = pst.executeQuery();

            while(rs.next()){
                Discipline d = new Discipline();
                d.setId(rs.getInt("id"));
                d.setName(rs.getString("nome"));
                d.setCode(rs.getString("codigo"));
                d.setDescription(rs.getString("descricao"));
                lista.add(d);
            }
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }
        return lista;
    }

}