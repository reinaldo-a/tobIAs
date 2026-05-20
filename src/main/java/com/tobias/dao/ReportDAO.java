package com.tobias.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.tobias.model.Report;

public class ReportDAO extends BaseDAO {

    public Report getBySubmissionId(int submissionId) {
        String sql = "SELECT id, aluno_id, atividade_id, submissao_id, titulo, data, avaliacao " +
                "FROM relatorio WHERE submissao_id = ?";

        try (Connection con = getConnection();
             PreparedStatement post = con.prepareStatement(sql)) {

            post.setInt(1, submissionId);

            try (ResultSet result = post.executeQuery()) {
                if (result.next()) {
                    return buildReport(result);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

    public Report getByActivityId(int activityId) {
        String sql = "SELECT id, aluno_id, atividade_id, submissao_id, titulo, data, avaliacao " +
                "FROM relatorio WHERE atividade_id = ?";

        try (Connection con = getConnection();
             PreparedStatement post = con.prepareStatement(sql)) {

            post.setInt(1, activityId);

            try (ResultSet result = post.executeQuery()) {
                if (result.next()) {
                    return buildReport(result);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

    public Integer saveForSubmission(int studentId, int submissionId, String title, String assessment) {
        String updateSql = "UPDATE relatorio SET aluno_id = ?, titulo = ?, data = CURRENT_DATE, avaliacao = ? " +
                "WHERE submissao_id = ?";
        String insertSql = "INSERT INTO relatorio(aluno_id, submissao_id, titulo, data, avaliacao) " +
                "VALUES (?, ?, ?, CURRENT_DATE, ?)";

        try (Connection con = getConnection();
             PreparedStatement update = con.prepareStatement(updateSql)) {

            update.setInt(1, studentId);
            update.setString(2, title);
            update.setString(3, assessment);
            update.setInt(4, submissionId);

            if (update.executeUpdate() > 0) {
                Report report = getBySubmissionId(submissionId);
                return report != null ? report.getId() : null;
            }

            try (PreparedStatement insert = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                insert.setInt(1, studentId);
                insert.setInt(2, submissionId);
                insert.setString(3, title);
                insert.setString(4, assessment);
                insert.executeUpdate();

                try (ResultSet keys = insert.getGeneratedKeys()) {
                    if (keys.next()) {
                        return keys.getInt(1);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

    public Integer saveForActivity(int activityId, String title, String assessment) {
        String updateSql = "UPDATE relatorio SET titulo = ?, data = CURRENT_DATE, avaliacao = ? " +
                "WHERE atividade_id = ?";
        String insertSql = "INSERT INTO relatorio(aluno_id, atividade_id, titulo, data, avaliacao) " +
                "VALUES (NULL, ?, ?, CURRENT_DATE, ?)";

        try (Connection con = getConnection();
             PreparedStatement update = con.prepareStatement(updateSql)) {

            update.setString(1, title);
            update.setString(2, assessment);
            update.setInt(3, activityId);

            if (update.executeUpdate() > 0) {
                Report report = getByActivityId(activityId);
                return report != null ? report.getId() : null;
            }

            try (PreparedStatement insert = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                insert.setInt(1, activityId);
                insert.setString(2, title);
                insert.setString(3, assessment);
                insert.executeUpdate();

                try (ResultSet keys = insert.getGeneratedKeys()) {
                    if (keys.next()) {
                        return keys.getInt(1);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

    private Report buildReport(ResultSet result) throws Exception {
        Report report = new Report();
        report.setId(result.getInt("id"));
        report.setStudentId(result.getInt("aluno_id"));
        report.setActivityId((Integer) result.getObject("atividade_id"));
        report.setSubmissionId((Integer) result.getObject("submissao_id"));
        report.setTitle(result.getString("titulo"));
        report.setAssessment(result.getString("avaliacao"));

        if (result.getDate("data") != null) {
            report.setDate(result.getDate("data").toLocalDate());
        }

        return report;
    }
}
