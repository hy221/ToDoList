package com.applicationcommunity;

public class ListItem {
    long id = 0;
    private String title = null;
    private String date = null;
    private String detail = null;
    private String key = null;

    long getId(){return id;}
    String getTitle() { return title; }
    String getDate() { return date; }
    String getDetail() { return detail; }
    String getKey() {return key;}

    void setId(long id) { this.id = id; }
    void setTitle(String title) { this.title = title; }
    void setDate(String date) { this.date = date; }
    void setDetail(String detail) { this.detail = detail; }
    void setKey(String key){this.key = key;}
}
