package com.tobias.model;

public class Discipline {
    private int id;
    private String name;
    private String code;
    private String description;
    private int idProfessor;

    public Discipline(){}

    public Discipline(int id, String name, String code, String description, int idProfessor){
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.idProfessor = idProfessor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(int idProfessor) {
        this.idProfessor = idProfessor;
    }

}
