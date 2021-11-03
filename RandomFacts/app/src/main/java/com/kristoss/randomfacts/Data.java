package com.kristoss.randomfacts;

public class Data {
    private String mainTitle, mainContext, url, question, answer;
 /*
 * Just a dummy file for test information. This file is going to be
 * removed when all information goes online.
 * */

    public Data(String mainTitle,String url, String mainContext) {
        this.mainTitle = mainTitle;
        this.mainContext = mainContext;
        this.url = url;
    }

    public Data(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public Data(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getMainContext() {
        return mainContext;
    }

    public void setMainContext(String mainContext) {
        this.mainContext = mainContext;
    }
}
