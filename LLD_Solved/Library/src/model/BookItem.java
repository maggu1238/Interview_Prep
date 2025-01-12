package model;

import java.awt.print.Book;
import java.util.*;

public class BookItem{
    private String bookItemId;
    private Date issueDate;
    private Date returnDate;
    private String issuerId;
    private BookDetails bookDetails;
    private Boolean isAvailable;

    public BookItem(BookDetails bookDetails, String bookItemId) {
        this.bookDetails = bookDetails;
        this.bookItemId =  bookItemId;  // Each copy gets a unique ID
    }

    // Getter for copyId
    public String getCopyId() {
        return bookItemId;
    }

    public void setIssuerId(String issuerId){
        this.issuerId = issuerId;
    }

    public String getIssuerId(){
        return issuerId;
    }

    public BookDetails getBookDetails() {
        return bookDetails;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }
}

