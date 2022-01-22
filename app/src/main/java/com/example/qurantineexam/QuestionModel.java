package com.example.qurantineexam;

public class QuestionModel {
    String question,marks;

    public QuestionModel(String question, String marks) {
        this.question = question;
        this.marks = marks;
    }

    public QuestionModel() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }
}
