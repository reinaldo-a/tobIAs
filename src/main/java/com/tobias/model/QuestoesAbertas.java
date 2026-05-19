package com.tobias.model;

public class QuestoesAbertas extends Questoes {

    private String respostaEsperada;

    public QuestoesAbertas(int id, float peso, String enunciado, int idActivity, String respostaEsperada) {
        super(id, peso, enunciado, idActivity);
        this.respostaEsperada = respostaEsperada;
    }

    public String getRespostaEsperada() {
        return respostaEsperada;
    }

    public void setRespostaEsperada(String respostaEsperada) {
        this.respostaEsperada = respostaEsperada;
    }

    @Override
    public String getTipo() {
        return "ABERTA";
    }
}
