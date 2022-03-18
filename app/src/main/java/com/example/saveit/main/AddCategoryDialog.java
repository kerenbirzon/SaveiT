package com.example.saveit.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.saveit.R;
import com.example.saveit.model.CategoryModel;
import com.google.android.material.textfield.TextInputLayout;

public class AddCategoryDialog extends Fragment {
    public static final int DEFAULT_ICON = 13; //R.drawable.buy
    private static final int CATEGORY_ICON_REQUEST_CODE = 111;
    private int iconImageValue = DEFAULT_ICON; //initialized to default icon
    private static final int[] iconImages = {R.drawable.money, R.drawable.tax, R.drawable.lipstick, R.drawable.id, R.drawable.house, R.drawable.garden, R.drawable.fish, R.drawable.fan, R.drawable.email, R.drawable.dog, R.drawable.car, R.drawable.cake, R.drawable.buy, R.drawable.cat, R.drawable.company};
    private String[] categoriesTitles; //categories titles not used for spinner
    private boolean isCategoryTitleValid = false;
    public static final int MAX_CATEGORY_NAME_LENGTH = 20;
    private static final String TAG = "AddCategoryFragment";

    public AddCategoryDialog() {
    }

    public AddCategoryDialog(String[] categoriesTitles) {
        this.categoriesTitles = categoriesTitles;
    }

    public interface OnInputListener {
        void sendInput(String title, int image);
    }

    public OnInputListener mOnInputListener;
    //widgets
    private TextInputLayout titleInput;
    private Button saveNewCategoryBtn, cancelNewCategoryBtn, chooseIconBtn;
    private Spinner titleSpinner;
    private ImageView iconPrevView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_category_dialog_fragment, container, false);
        saveNewCategoryBtn = view.findViewById(R.id.btn_action_ok);
        cancelNewCategoryBtn = view.findViewById(R.id.btn_action_cancel);
        chooseIconBtn = view.findViewById(R.id.btn_choose_icon);
        iconPrevView = view.findViewById(R.id.iv_icon_img_prev);
        titleInput = view.findViewById(R.id.et_title);
        titleInput.setVisibility(View.INVISIBLE);
        titleSpinner = view.findViewById(R.id.spinner_title);
        setCategoryTitle();

        cancelNewCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing dialog");
                getDialog().dismiss();
            }
        });
        saveNewCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: capturing input");
                if (userInputValid()) {
                    addNewCategory();
                } else {
                    Toast.makeText(getContext(), "invalid title", Toast.LENGTH_LONG).show();
                }
            }
        });


        chooseIconBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_addCategory_to_chooseIcon));

        return view;
    }

    private void saveCategory() {
        progressBar.setVisibility(View.VISIBLE);
        saveNewCategoryBtn.setEnabled(false);
        cancelNewCategoryBtn.setEnabled(false);
        String categoryName = categoryNameEt.getText().toString();
        Integer categoryImage = R.drawable.money;
        Log.d("TAG","saved categoryName:" + categoryName +" categoryImage:" + categoryImage);
        Category category = new Category(categoryName,categoryImage); // need to change the function
        CategoryModel.instance.addCategory(category,()->{
            Navigation.findNavController(categoryNameEt).navigateUp();
        });
    }

}