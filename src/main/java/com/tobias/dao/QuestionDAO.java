package com.tobias.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tobias.model.Questoes;
import com.tobias.model.QuestoesAbertas;
import com.tobias.model.QuestoesFechadas;

public class QuestionDAO extends BaseDAO {

    public void createQuestion(Questoes question) {
        String sql = "INSERT INTO questao(atividade_id, enunciado, peso) VALUES (?, ?, ?)";

        try (Connection con = getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement post = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                // Salva a questao vinculada ao id da atividade.
                post.setInt(1, question.getIdActivity());
                post.setString(2, question.getEnunciado());
                post.setFloat(3, question.getPeso());
                post.executeUpdate();

                try (ResultSet keys = post.getGeneratedKeys()) {
                    if (!keys.next()) {
                        con.rollback();
                        return;
                    }

                    int questionId = keys.getInt(1);
                    saveQuestionDetails(con, questionId, question);
                    con.commit();
                }
            } catch (Exception e) {
                con.rollback();
                System.out.println(e);
            } finally {
                con.setAutoCommit(true);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<Questoes> listByActivity(int activityId) {
        List<Questoes> questions = new ArrayList<>();
        String sql = "SELECT q.id, q.atividade_id, q.enunciado, q.peso, " +
                "d.resposta, me.opcao_correta, me.opcao_a, me.opcao_b, me.opcao_c, me.opcao_d " +
                "FROM questao q " +
                "LEFT JOIN dissertativa d ON q.id = d.questao_id " +
                "LEFT JOIN multipla_escolha me ON q.id = me.questao_id " +
                "WHERE q.atividade_id = ? " +
                "ORDER BY q.id";

        try (Connection con = getConnection();
             PreparedStatement post = con.prepareStatement(sql)) {

            post.setInt(1, activityId);

            try (ResultSet result = post.executeQuery()) {
                while (result.next()) {
                    questions.add(buildQuestion(result));
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return questions;
    }

    public Questoes getById(int id) {
        String sql = "SELECT q.id, q.atividade_id, q.enunciado, q.peso, " +
                "d.resposta, me.opcao_correta, me.opcao_a, me.opcao_b, me.opcao_c, me.opcao_d " +
                "FROM questao q " +
                "LEFT JOIN dissertativa d ON q.id = d.questao_id " +
                "LEFT JOIN multipla_escolha me ON q.id = me.questao_id " +
                "WHERE q.id = ?";

        try (Connection con = getConnection();
             PreparedStatement post = con.prepareStatement(sql)) {

            post.setInt(1, id);

            try (ResultSet result = post.executeQuery()) {
                if (result.next()) {
                    return buildQuestion(result);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

    public void updateQuestion(Questoes question) {
        String sql = "UPDATE questao SET enunciado = ?, peso = ? WHERE id = ?";

        try (Connection con = getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement post = con.prepareStatement(sql)) {
                post.setString(1, question.getEnunciado());
                post.setFloat(2, question.getPeso());
                post.setInt(3, question.getId());
                post.executeUpdate();

                deleteQuestionDetails(con, question.getId());
                saveQuestionDetails(con, question.getId(), question);
                con.commit();
            } catch (Exception e) {
                con.rollback();
                System.out.println(e);
            } finally {
                con.setAutoCommit(true);
            }
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

    private Questoes buildQuestion(ResultSet result) throws Exception {
        String correctOption = result.getString("opcao_correta");

        if (correctOption != null) {
            return new QuestoesFechadas(
                    result.getInt("id"),
                    result.getFloat("peso"),
                    result.getString("enunciado"),
                    result.getInt("atividade_id"),
                    correctOption,
                    result.getString("opcao_a"),
                    result.getString("opcao_b"),
                    result.getString("opcao_c"),
                    result.getString("opcao_d"));
        }

        return new QuestoesAbertas(
                result.getInt("id"),
                result.getFloat("peso"),
                result.getString("enunciado"),
                result.getInt("atividade_id"),
                result.getString("resposta"));
    }

    private void saveQuestionDetails(Connection con, int questionId, Questoes question) throws Exception {
        if (question instanceof QuestoesFechadas) {
            QuestoesFechadas closedQuestion = (QuestoesFechadas) question;
            String sql = "INSERT INTO multipla_escolha(questao_id, opcao_correta, opcao_a, opcao_b, opcao_c, opcao_d) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement post = con.prepareStatement(sql)) {
                post.setInt(1, questionId);
                post.setString(2, closedQuestion.getLetraCorreta());
                post.setString(3, closedQuestion.getOpcaoA());
                post.setString(4, closedQuestion.getOpcaoB());
                post.setString(5, closedQuestion.getOpcaoC());
                post.setString(6, closedQuestion.getOpcaoD());
                post.executeUpdate();
            }
            return;
        }

        QuestoesAbertas openQuestion = (QuestoesAbertas) question;
        String sql = "INSERT INTO dissertativa(questao_id, resposta) VALUES (?, ?)";

        try (PreparedStatement post = con.prepareStatement(sql)) {
            post.setInt(1, questionId);
            post.setString(2, openQuestion.getRespostaEsperada());
            post.executeUpdate();
        }
    }

    private void deleteQuestionDetails(Connection con, int questionId) throws Exception {
        try (PreparedStatement post = con.prepareStatement("DELETE FROM dissertativa WHERE questao_id = ?")) {
            post.setInt(1, questionId);
            post.executeUpdate();
        }

        try (PreparedStatement post = con.prepareStatement("DELETE FROM multipla_escolha WHERE questao_id = ?")) {
            post.setInt(1, questionId);
            post.executeUpdate();
        }
    }
}
