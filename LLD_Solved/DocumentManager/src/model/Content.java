package model;

public interface Content {
    void edit(String content);
    String getContent();
    Content clone();
}