package com.example.saveit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ChooseIconFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_icon, container, false);

        Button saveIconBtn = view.findViewById(R.id.btn_save_category_icon);
        saveIconBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save the category to the model

                //pop back to category list
                Navigation.findNavController(view).navigate(R.id.action_chooseIconFragment_pop);
            }
        });
        Button cancelIconBtn = view.findViewById(R.id.btn_action_cancel_icon_selection);
        cancelIconBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_chooseIconFragment_pop));


        return view;
    }
}