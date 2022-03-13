package com.example.saveit.document;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.saveit.R;

public class DocumentFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_document, container, false);
        Button saveDocumentBtn = view.findViewById(R.id.btn_save_document);
        saveDocumentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save the category to the model

                //pop back to category list
                Navigation.findNavController(view).navigate(R.id.action_documentFragment_pop);
            }
        });
        return view;
    }
}