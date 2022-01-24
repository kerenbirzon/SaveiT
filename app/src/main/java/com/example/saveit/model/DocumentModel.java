package com.example.saveit.model;

import java.util.LinkedList;
import java.util.List;

public class DocumentModel {

    public final static DocumentModel instance = new DocumentModel();

    private DocumentModel(){ }

    List<Document> documents = new LinkedList<Document>();

    public List<Document> getDocuments(String title) {
        documents = AppLocalDb.db.documentDao().getAllDocuments(title);
        return documents;
    }

    public void addDocument(Document document){
        AppLocalDb.db.documentDao().insertAll(document);
    }
}
