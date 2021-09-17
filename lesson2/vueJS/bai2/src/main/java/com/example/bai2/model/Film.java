package com.example.bai2.model;

public class Film {
    private String title;
    private String director;
    private int publishedYear;
    public Film(String title, String director, int publishedYear) {
        this.title = title;
        this.director = director;
        this.publishedYear = publishedYear;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        this.director = director;
    }
    public int getPublishedYear() {
        return publishedYear;
    }
    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }
    
}
