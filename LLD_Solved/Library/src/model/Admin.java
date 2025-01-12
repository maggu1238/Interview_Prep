package model;

import java.util.Map;

public class Admin {
    private String adminId;
    private String name;
    private String email;

    public Admin(String adminId, String name, String email) {
        this.adminId = adminId;
        this.name = name;
        this.email = email;
    }

    // Admin Functions
    public User registerUser(String userId, String name, String email) {
        return new User(userId, name, email);
    }

    public void removeUser(Map<String, User> users, String userId) {
        users.remove(userId);
    }

    public void addBook(Map<String, Book> books, String bookId, String title, String author, String genre) {
        books.put(bookId, new Book(bookId, title, author, genre));
    }
}