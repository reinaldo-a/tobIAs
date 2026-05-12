package com.tobias.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;  

import com.tobias.model.Professor;

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

    public Professor getOrCreateProfessor(int idUser){
        // Garante que exista um registro na tabela professor para esse usuario.
        getOrCreateTeacher(idUser);
        // Depois busca o objeto Professor completo, herdando os dados de usuario.
        return getProfessorByUserId(idUser);
    }

    public Professor getProfessorByUserId(int idUser){
        // Junta professor com usuario para montar a classe filha Professor.
        String sql = "SELECT p.id AS professor_id, p.especialidade, p.matricula_siape, " +
        "u.id AS usuario_id, u.nome, u.cpf, u.email, u.senha " +
        "FROM professor p " +
        "INNER JOIN usuario u ON p.usuario_id = u.id " +
        "WHERE u.id = ?";

        try{
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1,idUser);
            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                // O objeto Professor carrega os dados de usuario e tambem os dados especificos de professor.
                Professor professor = new Professor();
                professor.setProfessorId(rs.getInt("professor_id"));
                professor.setEspecialidade(rs.getString("especialidade"));
                professor.setMatriculaSiape(rs.getString("matricula_siape"));
                professor.setId(rs.getInt("usuario_id"));
                professor.setName(rs.getString("nome"));
                professor.setCpf(rs.getLong("cpf"));
                professor.setEmail(rs.getString("email"));
                professor.setPassword(rs.getString("senha"));
                con.close();
                return professor;
            }
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
}
