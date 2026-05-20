package com.tobias.dto;

public class GenerateQuestionsRequest {
    private int disciplineId;
    private String material;
    private String questionType;
    private int quantity;
    private String difficulty;


    public GenerateQuestionsRequest() {}


    public int getDisciplineId() { return disciplineId; }
    public void setDisciplineId(int disciplineId) { this.disciplineId = disciplineId; }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

    public String getQuestionType() { return questionType; }
    public void setQuestionType(String questionType) { this.questionType = questionType; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
}