package com.tobias.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.tobias.model.Question;

public class QuestionDAO extends BaseDAO {

    public void createQuestion(Question question) {
        String sql = "INSERT INTO questao(atividade_id, enunciado, peso) VALUES (?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement post = con.prepareStatement(sql)) {

            // Salva a questao vinculada ao id da atividade.
            post.setInt(1, question.getIdActivity());
            post.setString(2, question.getEnunciado());
            post.setFloat(3, question.getPeso());
            post.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<Question> listByActivity(int activityId) {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT id, atividade_id, enunciado, peso FROM questao WHERE atividade_id = ? ORDER BY id";

        try (Connection con = getConnection();
             PreparedStatement post = con.prepareStatement(sql)) {

            post.setInt(1, activityId);

            try (ResultSet result = post.executeQuery()) {
                while (result.next()) {
                    Question question = new Question(
                        result.getInt("id"),
                        result.getFloat("peso"),
                        result.getString("enunciado"),
                        result.getInt("atividade_id")
                    );

                    questions.add(question);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return questions;
    }

    public Question getById(int id) {
        String sql = "SELECT id, atividade_id, enunciado, peso FROM questao WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement post = con.prepareStatement(sql)) {

            post.setInt(1, id);

            try (ResultSet result = post.executeQuery()) {
                if (result.next()) {
                    return new Question(
                        result.getInt("id"),
                        result.getFloat("peso"),
                        result.getString("enunciado"),
                        result.getInt("atividade_id")
                    );
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

    public void updateQuestion(Question question) {
        String sql = "UPDATE questao SET enunciado = ?, peso = ? WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement post = con.prepareStatement(sql)) {

            post.setString(1, question.getEnunciado());
            post.setFloat(2, question.getPeso());
            post.setInt(3, question.getId());
            post.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteQuestion(int id) {
        String sql = "DELETE FROM questao WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement post = con.prepareStatement(sql)) {

            post.setInt(1, id);
            post.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
