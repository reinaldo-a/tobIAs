package com.tobias.model;

public class QuestoesFechadas extends Questoes {

    private String letraCorreta;
    private String opcaoA;
    private String opcaoB;
    private String opcaoC;
    private String opcaoD;

    public QuestoesFechadas(
            int id,
            float peso,
            String enunciado,
            int idActivity,
            String letraCorreta,
            String opcaoA,
            String opcaoB,
            String opcaoC,
            String opcaoD) {
        super(id, peso, enunciado, idActivity);
        this.letraCorreta = letraCorreta;
        this.opcaoA = opcaoA;
        this.opcaoB = opcaoB;
        this.opcaoC = opcaoC;
        this.opcaoD = opcaoD;
    }

    public String getLetraCorreta() {
        return letraCorreta;
    }

    public void setLetraCorreta(String letraCorreta) {
        this.letraCorreta = letraCorreta;
    }

    public String getOpcaoA() {
        return opcaoA;
    }

    public void setOpcaoA(String opcaoA) {
        this.opcaoA = opcaoA;
    }

    public String getOpcaoB() {
        return opcaoB;
    }

    public void setOpcaoB(String opcaoB) {
        this.opcaoB = opcaoB;
    }

    public String getOpcaoC() {
        return opcaoC;
    }

    public void setOpcaoC(String opcaoC) {
        this.opcaoC = opcaoC;
    }

    public String getOpcaoD() {
        return opcaoD;
    }

    public void setOpcaoD(String opcaoD) {
        this.opcaoD = opcaoD;
    }

    @Override
    public String getTipo() {
        return "FECHADA";
    }
}
