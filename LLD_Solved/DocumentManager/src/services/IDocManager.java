package services;

import model.*;
import model.Document.Document;
import model.User.RegisteredUser;


public interface IDocManager {
    void createDoc(RegisteredUser user, String documentId);
    Document viewDoc(RegisteredUser user, String documentId);
    void editDoc(RegisteredUser user, String documentId, Content content);
    void restoreToPrevVersion(RegisteredUser user, String documentId, String versionId);
}
