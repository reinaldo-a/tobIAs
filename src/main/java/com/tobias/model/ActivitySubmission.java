package com.tobias.model;

import java.time.LocalDateTime;

// Representa a entrega de uma atividade feita por um aluno.
// Guarda os dados gerais da submissao: atividade, aluno e data de envio.
public class ActivitySubmission {
    private int id;
    private int activityId;
    private int studentId;

    // Dados do aluno usados para exibir a entrega sem precisar buscar o aluno separadamente.
    private String studentName;
    private String studentEmail;

    // Momento em que o aluno enviou a atividade.
    private LocalDateTime submittedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}
