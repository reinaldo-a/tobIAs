package com.tobias.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.tobias.model.Report;

public class ReportDAO extends BaseDAO {

    public Report getBySubmissionId(int submissionId) {
        String sql = "SELECT id, aluno_id, submissao_id, titulo, data, avaliacao " +
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

    public Integer saveForSubmission(int studentId, int submissionId, String title, String assessment) {
        String sql = "INSERT INTO relatorio(aluno_id, submissao_id, titulo, data, avaliacao) " +
                "VALUES (?, ?, ?, CURRENT_DATE, ?) " +
                "ON CONFLICT (submissao_id) WHERE submissao_id IS NOT NULL " +
                "DO UPDATE SET titulo = EXCLUDED.titulo, data = CURRENT_DATE, avaliacao = EXCLUDED.avaliacao " +
                "RETURNING id";

        try (Connection con = getConnection();
             PreparedStatement post = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            post.setInt(1, studentId);
            post.setInt(2, submissionId);
            post.setString(3, title);
            post.setString(4, assessment);

            try (ResultSet result = post.executeQuery()) {
                if (result.next()) {
                    return result.getInt("id");
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
        report.setSubmissionId((Integer) result.getObject("submissao_id"));
        report.setTitle(result.getString("titulo"));
        report.setAssessment(result.getString("avaliacao"));

        if (result.getDate("data") != null) {
            report.setDate(result.getDate("data").toLocalDate());
        }

        return report;
    }
}
