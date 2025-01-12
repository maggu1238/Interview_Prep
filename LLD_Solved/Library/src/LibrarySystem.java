import model.*;
import strategies.Issue.IssueStrategy;

import java.util.*;

class LibrarySystem {
    private final IssueStrategy issueStrategy;
    private Map<String, BookDetails> bookDetailsData = new HashMap<>();
    private Map<String, User> users = new HashMap<>();
    private List<BookItem> issuedBookItems = new ArrayList<>();
    private Map<String, BookItem> books;
    private Admin admin;

    public LibrarySystem(Admin admin, IssueStrategy issueStrategy) {
        this.admin = admin;
        this.issueStrategy = issueStrategy;
    }

    // Admin Functions
    public void addUser(String userId, String name, String email) {
        User newUser = admin.registerUser(userId, name, email);
        users.put(userId, newUser);
    }

    public void removeUser(String userId) {
        admin.removeUser(users, userId);
    }

    // Add a book with the initial number of copies
    // Add a new book to the library
    public void addBooks(String ISBN, String title, String author, String genre, int numCopies) {
        BookDetails bookDetails = bookDetailsData.get(ISBN);

        if (bookDetails == null) {
            // If the book doesn't exist, create a new BookDetails object
            bookDetails = new BookDetails(ISBN, title, author, genre);
            bookDetailsData.put(ISBN, bookDetails);
            System.out.println("New book added: " + title);
        } else {
            System.out.println("Book details already exists. Adding more copies.");
        }

        // Add copies for the book
        for (int i = 0; i < numCopies; i++) {
            String bookItemId = UUID.randomUUID().toString(); // Generate a unique ID for each copy
            BookItem newCopy = new BookItem(bookDetails, bookItemId);
            books.put(bookItemId, newCopy);
        }

        System.out.println(numCopies + " copies added for book: " + title);
    }


    // User Functions
    public List<BookItem> searchBooks(String query) {
        List<BookItem> result = new ArrayList<>();
        for (BookItem bookItem : books.values()) {
            BookDetails bookDetails =  bookItem.getBookDetails();
            if (bookDetails.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                bookDetails.getAuthor().toLowerCase().contains(query.toLowerCase()) ||
                bookDetails.getGenre().toLowerCase().contains(query.toLowerCase())) {
                result.add(bookItem);
            }
        }
        return result;
    }

    // Issue a book based on the selected strategy
    public synchronized boolean issueBook(String userId, String bookId) {
        BookItem book = books.get(bookId);
        if (book == null || !book.getAvailable()) {
            System.out.println("Book not available or doesn't exist.");
            return false;
        }

        Queue<String> queue = waitlist.computeIfAbsent(bookId, k -> new LinkedList<>());
        if (!queue.isEmpty() && !queue.contains(userId)) {
            System.out.println("Adding user " + userId + " to the waitlist for book with ISBN " + ISBN);
            queue.add(userId);
            return true;
        }

        synchronized (book) {
            if (!book.getAvailable()) {
                System.out.println("Book already borrowed.");
                return false;
            }

            User selectedUser = issueStrategy.selectUser(queue);
            selectedUser.borrowBook(book);
            System.out.println("Book issued to user: " + selectedUser.getName());
            return true;
        }
    }

    public boolean returnBook(String userId, String bookId) {
        User user = users.get(userId);
        Book book = books.get(bookId);

        if (user == null || book == null) {
            System.out.println("User or Book not found.");
            return false;
        }

        // Synchronize on the specific book object
        synchronized (book) {
            if (book.isAvailable()) {
                System.out.println("Book is not currently borrowed.");
                return false;
            }

            user.returnBook(book);
            System.out.println("Book returned successfully by user: " + userId);
            return true;
        }
    }




}