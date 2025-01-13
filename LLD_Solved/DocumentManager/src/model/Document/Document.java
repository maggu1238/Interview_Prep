package model.Document;

import model.User.RegisteredUser;

public class Document {
    private final String docId;
    private final RegisteredUser createdBy;

    public Document(String docId, RegisteredUser createdBy){
        this.docId = docId;
        this.createdBy = createdBy;
    }

    public RegisteredUser getCreatedBy() {
        return createdBy;
    }
}

