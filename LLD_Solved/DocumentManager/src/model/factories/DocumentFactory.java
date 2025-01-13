package model.factories;

import model.Document.Document;
import model.Document.TextDocument;
import model.User.RegisteredUser;

public class DocumentFactory {

    DocumentFactory(VersionManager versionManager){
        this.versionManager = versionManager;
    }

    public Document createDocument(String id, RegisteredUser registeredUser){
        return new TextDocument(id, registeredUser, versionManager);
    }
}
