package com.example.saveit.document;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.saveit.R;

public class DocumentFragment extends Fragment {

    Button saveDocumentBtn,documentImageBtn;
    EditText documentNameEt,documentCommentEt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_document, container, false);

        saveDocumentBtn = view.findViewById(R.id.btn_save_document);
        documentImageBtn = view.findViewById(R.id.document_add_img_btn);
        documentNameEt = view.findViewById(R.id.document_name_et);
        documentCommentEt = view.findViewById(R.id.document_comment_et);

        saveDocumentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigateUp();
            }
        });
        return view;
    }
}