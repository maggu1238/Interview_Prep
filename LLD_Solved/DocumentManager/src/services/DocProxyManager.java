package services;

import model.Content;
import model.Document.Document;
import model.User.RegisteredUser;

public class DocProxyManager implements IDocManager{
    private DocManager docManager;

    public DocProxyManager(DocManager docManager){
        this.docManager = docManager;
    }

    @Override
    public void createDoc(RegisteredUser user, String documentId) {
        docManager.createDoc(user, documentId);
    }

    @Override
    public Document viewDoc(RegisteredUser user, String documentId) {
        return docManager.viewDoc(user, documentId);
    }

    @Override
    public void editDoc(RegisteredUser user, String documentId, Content content) {
        if(!isAuthorized(user, documentId)) {
            throw new RuntimeException("Unauthorized access!");
        }

        docManager.editDoc(user,documentId, content);
    }

    @Override
    public void restoreToPrevVersion(RegisteredUser user, String documentId, String versionId) {
        if(!isAuthorized(user, documentId)) {
            throw new RuntimeException("Unauthorized access!");
        }

        docManager.restoreToPrevVersion(user,documentId, versionId);
    }

    private boolean isAuthorized(RegisteredUser user, String documentId) {
        return docManager.getDocumentData().getDocumentByDocumentId(documentId).getCreatedBy().getUserId().equals(user.getUserId());
    }

}
