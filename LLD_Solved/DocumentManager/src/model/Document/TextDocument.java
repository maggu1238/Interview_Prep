package model.Document;

import model.Content;
import model.User.RegisteredUser;

public class TextDocument extends  Document{
    private Content content;
    private VersionManager versionManager;
    public TextDocument(String docId, RegisteredUser createdBy, Content content, VersionManager versionManager) {
        super(docId, createdBy);
        this.content = content;
        this.versionManager = versionManager;
    }
}
