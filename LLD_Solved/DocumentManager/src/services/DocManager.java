package services;

import data.DocumentData;
import model.Content;
import model.Document.Document;
import model.Document.TextDocument;
import model.User.RegisteredUser;
import model.factories.DocumentFactory;

public class DocManager implements IDocManager{

    private DocumentFactory  documentFactory;
    private DocumentData documentData;

    public DocManager(DocumentData documentData, DocumentFactory documentFactory){
        this.documentFactory = documentFactory;
        this.documentData = documentData;
    }

    @Override
    public void createDoc(RegisteredUser user, String documentId) {
        TextDocument  document = (TextDocument) documentFactory.createDocument(documentId, user);
        documentData.putDocument(documentId, document);
    }

    @Override
    public Document viewDoc(RegisteredUser user, String documentId) {
        return documentData.getDocumentByDocumentId(documentId);
    }

    @Override
    public void editDoc(RegisteredUser user, String documentId, Content content) {
        TextDocument document = documentData.getDocumentByDocumentId(documentId);
        document.setContent(content);
        document.
    }

    @Override
    public void restoreToPrevVersion(RegisteredUser user, String documentId, String versionId) {

    }

    public DocumentData getDocumentData() {
        return documentData;
    }
}
