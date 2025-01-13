package services;

import model.Content;
import model.Document.Document;
import model.User.RegisteredUser;

public class DocManager implements IDocManager{

    @Override
    public void createDoc(RegisteredUser user, Document document) {

    }

    @Override
    public Document viewDoc(RegisteredUser user, Document document) {

        return null;
    }

    @Override
    public void editDoc(RegisteredUser user, Document document, Content content) {

    }

    @Override
    public void restoreToPrevVersion(RegisteredUser user, Document document, String versionId) {

    }
}
