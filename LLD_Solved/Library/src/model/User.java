package model;

import java.util.*;

public class User {
    private String userId;
    private String name;
    private String email;
    private List<BookItem> borrowedBooks;

    public User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.borrowedBooks = new ArrayList<>();
    }

    // Getters and Actions
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public void borrowBook(BookItem book) {
        borrowedBooks.add(book);
    }
    public void returnBook(BookItem book) {
        borrowedBooks.remove(book);
    }
}