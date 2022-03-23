package com.example.saveit.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.saveit.R;


public class EditCategoryFragment extends Fragment {

    public static final int DEFAULT_ICON = 13; //R.drawable.buy
    private static final int CATEGORY_ICON_REQUEST_CODE = 111;
    private int iconImageValue = DEFAULT_ICON;
    private static final int[] iconImages = {R.drawable.money, R.drawable.tax, R.drawable.lipstick, R.drawable.id, R.drawable.house, R.drawable.garden, R.drawable.fish, R.drawable.fan, R.drawable.email, R.drawable.dog, R.drawable.car, R.drawable.cake, R.drawable.buy, R.drawable.cat, R.drawable.company};

    private EditText categoryNameEt;
    private Button saveChangedCategoryBtn, cancelChangedCategoryBtn, chooseChangedIconBtn;
    private ProgressBar progressBar;
    private ImageView iconPrevViewChanged;
    private int iconLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_category, container, false);

        saveChangedCategoryBtn = view.findViewById(R.id.btn_change_action_save);
        cancelChangedCategoryBtn = view.findViewById(R.id.btn_change_action_cancel);
        chooseChangedIconBtn = view.findViewById(R.id.btn_change_icon);
        iconPrevViewChanged = view.findViewById(R.id.iv_icon_img_prev_change);
        categoryNameEt = view.findViewById(R.id.change_Category_name_et);
        progressBar = view.findViewById(R.id.change_category_progressBar);
        progressBar.setVisibility(View.GONE);

        saveChangedCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("clicked", "save new category was clicked");
                changeCategory();
            }
        });

        cancelChangedCategoryBtn.setOnClickListener((v) -> {
            Navigation.findNavController(v).navigateUp();
        });

        chooseChangedIconBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_editCategoryFragment_to_chooseIconFragment));


        return view;
    }

    private void changeCategory() {
        progressBar.setVisibility(View.VISIBLE);
//        iconLocation = AddCategoryFragmentArgs.fromBundle(getArguments()).getImageView();
//        iconPrevView.setImageResource(iconImages[iconLocation]);
//        saveNewCategoryBtn.setEnabled(false);
//        cancelNewCategoryBtn.setEnabled(false);
//        categoryName = categoryNameEt.getText().toString();
//        categoryImage = String.valueOf(iconImages[iconLocation]);
//        Log.d("TAG","saved categoryName:" + categoryName +" categoryImage:" + categoryImage);
//        Category category = new Category(categoryName,categoryImage,false); // need to change the function
//        CategoryModel.instance.addCategory(category,()->{
//            Navigation.findNavController(categoryNameEt).navigateUp();
//        });
    }
}