package com.example.saveit.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.saveit.R;
import com.example.saveit.model.Category;
import com.example.saveit.model.CategoryModel;
import com.google.android.material.textfield.TextInputLayout;

public class AddCategoryFragment extends Fragment {
    public static final int DEFAULT_ICON = 13; //R.drawable.buy
    private int iconImageValue = DEFAULT_ICON; //initialized to default icon
    private static final int[] iconImages = {R.drawable.money, R.drawable.tax, R.drawable.lipstick, R.drawable.id, R.drawable.house, R.drawable.garden, R.drawable.fish, R.drawable.fan, R.drawable.email, R.drawable.dog, R.drawable.car, R.drawable.cake, R.drawable.buy, R.drawable.cat, R.drawable.company};
    private String[] categoriesTitles; //categories titles not used for spinner

    private EditText categoryNameEt;
    private Button saveNewCategoryBtn, cancelNewCategoryBtn, chooseIconBtn;
    private ImageView iconPrevView;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_category, container, false);
        saveNewCategoryBtn = view.findViewById(R.id.btn_action_save);
        cancelNewCategoryBtn = view.findViewById(R.id.btn_action_cancel);
        chooseIconBtn = view.findViewById(R.id.btn_choose_icon);
        iconPrevView = view.findViewById(R.id.iv_icon_img_prev);
        categoryNameEt = view.findViewById(R.id.add_Category_name_et);
        progressBar = view.findViewById(R.id.add_category_progressBar);
        progressBar.setVisibility(View.GONE);



        saveNewCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("clicked", "save new category was clicked");
                saveCategory();
            }
        });

        cancelNewCategoryBtn.setOnClickListener((v) -> {
            Log.d("AddCategoryFragment", "onClick: closing AddCategoryFragment");
            Navigation.findNavController(v).navigateUp();
        });

        chooseIconBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_addCategory_to_chooseIcon));

        return view;
    }

    private void saveCategory() {
        progressBar.setVisibility(View.VISIBLE);
        saveNewCategoryBtn.setEnabled(false);
        cancelNewCategoryBtn.setEnabled(false);
        String categoryName = categoryNameEt.getText().toString();
        int categoryImage = R.drawable.money;
        Log.d("TAG","saved categoryName:" + categoryName +" categoryImage:" + categoryImage);
        Category category = new Category(categoryName,categoryImage); // need to change the function
        CategoryModel.instance.addCategory(category,()->{
            Navigation.findNavController(categoryNameEt).navigateUp();
        });
    }

}