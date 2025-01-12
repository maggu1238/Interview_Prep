package model;

public class BookDetails {
    private String ISBN;
    private String title;
    private String author;
    private String genre;

    public BookDetails(String ISBN, String title, String author, String genre) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    // Getters and Setters
    public String getISBN() { return ISBN; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }

}