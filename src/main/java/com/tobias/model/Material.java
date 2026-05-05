package com.tobias.model;

public class Material {
    private int id;
    private String titulo;
    private String conteudo;
    private Disciplina disciplina; // composição

    // Construtores
    public Material() {}
    public Material(int id, String titulo, String conteudo, Disciplina disciplina) {
        this.id = id;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.disciplina = disciplina;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }

    public Disciplina getDisciplina() { return disciplina; }
    public void setDisciplina(Disciplina disciplina) { this.disciplina = disciplina; }

    // Operação prevista no diagrama
    public void editarMaterial(String novoTitulo, String novoConteudo) {
        this.titulo = novoTitulo;
        this.conteudo = novoConteudo;
    }
}
