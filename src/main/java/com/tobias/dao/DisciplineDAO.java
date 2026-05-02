package com.tobias.dao;

import com.tobias.model.Discipline;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DisciplineDAO extends BaseDAO{

    public void save(Discipline discipline){

        String add = "INSERT INTO disciplina(nome, codigo, descricao,professor_id) VALUES (?,?,?,?)";

        try{
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement(add);
            pst.setString(1,discipline.getName());
            pst.setString(2,discipline.getCode());
            pst.setString(3,discipline.getDescription());
            pst.setInt(4,discipline.getIdProfessor());
            pst.executeUpdate();
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public List<Discipline> listDisciplines(int idUser){
        List<Discipline> lista = new ArrayList<>();
        String read = "SELECT d.id,d.nome,d.codigo,d.descricao, u_prof.nome AS professor_nome "+
        "FROM disciplina d "+
        "LEFT JOIN professor p ON d.professor_id = p.id "+
        "LEFT JOIN usuario u_prof ON p.usuario_id = u_prof.id "+
        "WHERE p.usuario_id = ? "+
        "OR d.id IN (SELECT m.disciplina_id FROM matricula m " +
        "INNER JOIN aluno a ON m.aluno_id = a.id WHERE a.usuario_id = ?) " +
        "ORDER BY d.id DESC";
        try{
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement(read);
            pst.setInt(1,idUser);
            pst.setInt(2,idUser);
            ResultSet rs = pst.executeQuery();

            while(rs.next()){
                Discipline d = new Discipline();
                d.setId(rs.getInt("id"));
                d.setName(rs.getString("nome"));
                d.setCode(rs.getString("codigo"));
                d.setDescription(rs.getString("descricao"));

                String teacherName = rs.getString("professor_nome");
                d.setProfessorName(teacherName);
                lista.add(d);
            }
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }
        return lista;
    }

    //buscar disciplina pelo código
    public int getByCode(String code){
        String search = "SELECT id FROM disciplina WHERE codigo = ?";
        try{
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement(search);
            pst.setString(1,code);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                int id = rs.getInt("id");
                return id;
            }
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }
        return -1;
    }

    //inserir o aluno na tabela matricula junto com disciplina
    public void enrollStudent(int idStudent, int idDiscipline){
        String insert = "INSERT INTO matricula(aluno_id,disciplina_id) VALUES(?,?)";
        try{
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement(insert);
            pst.setInt(1,idStudent);
            pst.setInt(2,idDiscipline);
            pst.executeUpdate();
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

}
