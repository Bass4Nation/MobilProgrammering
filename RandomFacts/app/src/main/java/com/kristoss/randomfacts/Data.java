package com.kristoss.randomfacts;

public class Data {
    private String mainTitle, mainContext, url;


    public Data(String mainTitle,String url, String mainContext) {
        this.mainTitle = mainTitle;
        this.mainContext = mainContext;
        this.url = url;
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
