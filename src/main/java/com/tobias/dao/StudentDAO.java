package com.tobias.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement; 

import com.tobias.model.Aluno;

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

    public Aluno getOrCreateAluno(int idUser){
        // Garante que exista um registro na tabela aluno para esse usuario.
        getOrCreateStudent(idUser);
        // Depois busca o objeto Aluno completo, herdando os dados de usuario.
        return getAlunoByUserId(idUser);
    }

    public Aluno getAlunoByUserId(int idUser){
        // Junta aluno com usuario para montar a classe filha Aluno.
        String sql = "SELECT a.id AS aluno_id, a.matricula, " +
        "u.id AS usuario_id, u.nome, u.cpf, u.email, u.senha " +
        "FROM aluno a " +
        "INNER JOIN usuario u ON a.usuario_id = u.id " +
        "WHERE u.id = ?";

        try{
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1,idUser);
            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                // O objeto Aluno carrega os dados de usuario e tambem os dados especificos de aluno.
                Aluno aluno = new Aluno();
                aluno.setStudentId(rs.getInt("aluno_id"));
                aluno.setMatricula(rs.getString("matricula"));
                aluno.setId(rs.getInt("usuario_id"));
                aluno.setName(rs.getString("nome"));
                aluno.setCpf(rs.getString("cpf"));
                aluno.setEmail(rs.getString("email"));
                aluno.setPassword(rs.getString("senha"));
                con.close();
                return aluno;
            }
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
}
