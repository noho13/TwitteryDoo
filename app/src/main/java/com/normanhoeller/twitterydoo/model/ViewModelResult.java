package com.normanhoeller.twitterydoo.model;

/**
 * Created by norman on 02/02/16.
 */
public class ViewModelResult {

    private final String text;
    private final String date;
    private final String url;

    public ViewModelResult(String url, String text, String date) {
        this.url = url;
        this.text = text;
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }
}
