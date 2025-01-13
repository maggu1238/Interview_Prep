package services;

import model.*;
import model.Document.Document;
import model.User.RegisteredUser;


public interface IDocManager {
    void createDoc(RegisteredUser user, Document document);
    Document viewDoc(RegisteredUser user, Document document);
    void editDoc(RegisteredUser user, Document document, Content content);
    void restoreToPrevVersion(RegisteredUser user, Document document, String versionId);
}
