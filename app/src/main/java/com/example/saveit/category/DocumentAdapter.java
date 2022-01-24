package com.example.saveit.category;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saveit.model.Document;
import com.example.saveit.R;

import java.util.ArrayList;
import java.util.List;


public class DocumentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Document> documents;
    private DocumentClickListener documentClickListener;
    private DocumentLongClickListener documentLongClickListener;

    DocumentAdapter(List<Document> items) {
        documents = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View gridItem = inflater.inflate(R.layout.document_item, parent, false);
        return new DocumentItemHolder(gridItem);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Document docItem = documents.get(position);
        DocumentItemHolder docHolder = ((DocumentItemHolder) holder);
        docHolder.title.setText(docItem.getTitle());
        docHolder.expirationDate.setText(docItem.getExpirationDate());
        if (docItem.getHasAlarm()){
            docHolder.alarmImg.setImageResource(R.drawable.ic_alarm_on);
        }
        else {
            docHolder.alarmImg.setImageResource(R.drawable.ic_alarm_off);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (documentClickListener != null) {
                    documentClickListener.onDocumentClicked(position);
                }
            }
        });

        // set long click listener
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (documentLongClickListener != null) {
                    documentLongClickListener.onDocumentLongClicked(position);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return documents.size();
    }


    public void setDocumentClickListener(DocumentClickListener documentClickListener) {
        this.documentClickListener = documentClickListener;
    }

    public void setDocumentLongClickListener(DocumentLongClickListener documentLongClickListener) {
        this.documentLongClickListener = documentLongClickListener;
    }


    public void deleteDocument(Document document) {
        documents.remove(document);
    }
}

class DocumentItemHolder extends RecyclerView.ViewHolder {
    TextView title;
    TextView expirationDate;
    ImageView alarmImg;

    public DocumentItemHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.document_title);
        expirationDate = itemView.findViewById(R.id.tv_expiry_date);
        alarmImg = itemView.findViewById(R.id.iv_alarm);
    }
}
