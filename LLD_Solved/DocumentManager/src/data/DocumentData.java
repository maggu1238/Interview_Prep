package data;

import model.Document.Document;
import model.Document.TextDocument;

import java.util.Map;
import java.util.Objects;

public class DocumentData {
    Map<String, TextDocument> documentList;

    public void putDocument(String documentId, TextDocument document){
        documentList.put(documentId, document);
    }
    public TextDocument getDocumentByDocumentId(String documentId){
        Document document = documentList.get(documentId);
        if(Objects.isNull(documentId)){
            System.out.println("No document found with id- " + documentId);
        }
        return document;
    }
}
