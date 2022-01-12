package com.example.saveit.model;

import java.util.ArrayList;

public class DocumentModel {

    public final static DocumentModel instance = new DocumentModel();

    private DocumentModel(){ }

    ArrayList<Document> documents = new ArrayList<Document>();

    public ArrayList<Document> getDocuments() {
        return documents;
    }
}
