package com.example.saveit.model;

import java.util.ArrayList;

public class DocumentModel {

    public final static DocumentModel instance = new DocumentModel();

    private DocumentModel(){ }

    ArrayList<Document> documents = new ArrayList<Document>();

    public ArrayList<Document> getDocuments() {
        documents = AppLocalDb.db.documentDao().getAllDocuments();
        return documents;
    }

    public void addDocument(Document document){
        AppLocalDb.db.documentDao().insertAll(document);
    }
}
