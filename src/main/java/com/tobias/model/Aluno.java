package com.tobias.model;

public class Aluno extends User {
    // Id da tabela aluno. O id herdado de User continua sendo o id da tabela usuario.
    private int studentId;
    private String matricula;

    public Aluno() {
        super();
    }

    public Aluno(User user, int studentId) {
        // Copia os dados do usuario base para transformar esse usuario em Aluno.
        super(user.getName(), user.getCpf(), user.getEmail(), user.getPassword(), user.getId());
        this.studentId = studentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    @Override
    public String getRoleName() {
        // Polimorfismo: quando tratado como User, o Aluno ainda informa seu papel real.
        return "ALUNO";
    }

    @Override
    public boolean canSubmitActivity() {
        // Aluno pode enviar respostas das atividades.
        return true;
    }
}
