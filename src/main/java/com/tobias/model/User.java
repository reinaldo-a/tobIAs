package com.tobias.model;

public class User {
    private String name;
    private long cpf;
    private String email;
    private String password;
    private int id;
    private String photo;

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

    public String getPhoto(){
        return this.photo;
    }

    public void setPhoto(String photo){
        this.photo = photo;
    }
}
