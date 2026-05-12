package com.tobias.model;

public class User {
    private String name;
    private long cpf;
    private String email;
    private String password;
    private int id;

    public User() {
    
    }
    
    public User(String name, long cpf, String email, String password, int id) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCpf() {
        return cpf;
    }

    public void setCpf(long cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Metodo padrao do usuario generico. As subclasses Aluno e Professor sobrescrevem.
    public String getRoleName() {
        return "USUARIO";
    }

    // Por padrao, um usuario comum nao gerencia disciplina.
    public boolean canManageDiscipline() {
        return false;
    }

    // Por padrao, um usuario comum nao envia atividade diretamente.
    public boolean canSubmitActivity() {
        return false;
    }
    
}
