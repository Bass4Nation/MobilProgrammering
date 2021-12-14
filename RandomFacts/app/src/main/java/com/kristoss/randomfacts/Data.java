package com.kristoss.randomfacts;

public class Data {
    private String question, answer;

    public Data(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }

}
