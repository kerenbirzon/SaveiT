package com.example.saveit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class AddCategoryFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_category, container, false);
        Button saveNewCategoryBtn = view.findViewById(R.id.btn_action_save);
        saveNewCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save the category to the model

                //pop back to category list
                Navigation.findNavController(view).navigate(R.id.action_addCategoryFragment_pop);
            }
        });
        Button cancelNewCategoryBtn = view.findViewById(R.id.btn_action_cancel);
        cancelNewCategoryBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_addCategoryFragment_pop));

        Button chooseIconBtn = view.findViewById(R.id.btn_choose_icon);
        chooseIconBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_addCategory_to_chooseIcon));

        return view;
    }
}