package com.tobias.model;

public class Professor extends User {
    // Id da tabela professor. O id herdado de User continua sendo o id da tabela usuario.
    private int professorId;
    private String especialidade;
    private String matriculaSiape;

    public Professor() {
        super();
    }

    public Professor(User user, int professorId) {
        // Copia os dados do usuario base para transformar esse usuario em Professor.
        super(user.getName(), user.getCpf(), user.getEmail(), user.getPassword(), user.getId());
        this.professorId = professorId;
    }

    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getMatriculaSiape() {
        return matriculaSiape;
    }

    public void setMatriculaSiape(String matriculaSiape) {
        this.matriculaSiape = matriculaSiape;
    }

    @Override
    public String getRoleName() {
        // Polimorfismo: quando tratado como User, o Professor ainda informa seu papel real.
        return "PROFESSOR";
    }

    @Override
    public boolean canManageDiscipline() {
        // Professor pode criar/editar recursos da disciplina.
        return true;
    }
}
