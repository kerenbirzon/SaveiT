package com.example.saveit.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.saveit.R;
import com.example.saveit.model.Category;
import com.google.android.material.textfield.TextInputLayout;

public class AddCategoryFragment extends Fragment {
    public static final int DEFAULT_ICON = 13; //R.drawable.buy
    private int iconImageValue = DEFAULT_ICON; //initialized to default icon
    private static final int[] iconImages = {R.drawable.money, R.drawable.tax, R.drawable.lipstick, R.drawable.id, R.drawable.house, R.drawable.garden, R.drawable.fish, R.drawable.fan, R.drawable.email, R.drawable.dog, R.drawable.car, R.drawable.cake, R.drawable.buy, R.drawable.cat, R.drawable.company};
    private String[] categoriesTitles; //categories titles not used for spinner

    //widgets
    private TextInputLayout titleInput;
    private Button saveNewCategoryBtn, cancelNewCategoryBtn, chooseIconBtn;
    private Spinner titleSpinner;
    private ImageView iconPrevView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_category, container, false);
        saveNewCategoryBtn = view.findViewById(R.id.btn_action_save);
        cancelNewCategoryBtn = view.findViewById(R.id.btn_action_cancel);
        chooseIconBtn = view.findViewById(R.id.btn_choose_icon);
        iconPrevView = view.findViewById(R.id.iv_icon_img_prev);
        titleInput = view.findViewById(R.id.et_title);
        titleSpinner = view.findViewById(R.id.spinner_title);
        titleInput.setVisibility(View.INVISIBLE);


        saveNewCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("clicked", "save new category was clicked");
                //if (userInputValid()) {
                //    addNewCategory();
                //} else {
                //    Toast.makeText(getContext(), "invalid title", Toast.LENGTH_LONG).show();
                //}

                //Navigation.findNavController(view).navigate(CategoryListFragmentDirections.actionCategoryListToCategory(category.getTitle()));


            }
        });
        cancelNewCategoryBtn.setOnClickListener((v) -> {
            Log.d("AddCategoryFragment", "onClick: closing AddCategoryFragment");
            Navigation.findNavController(v).navigateUp();
        });

        chooseIconBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_addCategory_to_chooseIcon));

        return view;
    }

}