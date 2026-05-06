package com.tobias.model;

public class Question {
    
    private int id;
    private float peso;
    private String enunciado;
    private int idActivity;


    public Question(int id, float peso, String enunciado, int idAtivity) {
        this.id = id;
        this.peso = peso;
        this.enunciado = enunciado;
        this.idActivity = idAtivity;
        
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public float getPeso() {
        return peso;
    }
    public void setPeso(float peso) {
        this.peso = peso;
    }
    public String getEnunciado() {
        return enunciado;
    }
    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public void setIdActivity(int idActivity) {
        this.idActivity = idActivity;
    }

    public int getIdActivity() {
        return idActivity;
    }

    
}
