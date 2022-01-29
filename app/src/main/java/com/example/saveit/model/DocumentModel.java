package com.example.saveit.model;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DocumentModel {

    public final static DocumentModel instance = new DocumentModel();

    ModelFirebase modelFirebase = new ModelFirebase();
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
