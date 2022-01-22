package com.example.qurantineexam;

public class Test {
    String Question, Answer;

    public Test(String question, String answer) {
        Question = question;
        Answer = answer;
    }

    public Test() {
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }
}
