package com.example.saveit.main;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.saveit.R;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputLayout;

import com.example.saveit.model.CategoryModel;
import androidx.navigation.Navigation;
// TODO: FIX NAVIGATION

public class AddCategoryDialog extends DialogFragment {
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


        chooseIconBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: chooseIconButton");
                ChooseIconFragment chooseIconFragment = new ChooseIconFragment();
                chooseIconFragment.setTargetFragment(AddCategoryDialog.this, 111);
                chooseIconFragment.show(getFragmentManager(), "dialog");
            }
        });

        return view;
    }

    /*
     * checks if user input is valid
     */
    private boolean userInputValid() {
        return isCategoryTitleValid();
    }

    /*
     * checks if category title is valid
     */
    private boolean isCategoryTitleValid() {
        String title = titleInput.getEditText().getText().toString();
        boolean isTitleChosen = false;
        if (title.equals("")) {
            title = titleSpinner.getSelectedItem().toString();
            if (!title.equals("Choose Name…") && !title.equals("Other")) {
                isTitleChosen = true;
            }
        }
        return isCategoryTitleValid || isTitleChosen;
    }
    /*
     * add a new category method
     */
    private void addNewCategory() {
        String title = titleInput.getEditText().getText().toString();
        if (title == null || title.equals("")) {
            title = titleSpinner.getSelectedItem().toString();
        }
        mOnInputListener.sendInput(title, iconImageValue);
        getDialog().dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnInputListener = (OnInputListener) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage());
        }
    }

    /**
     * Handles the event where the user chooses a category name
     */
    private void setCategoryTitle() {
        final ArrayAdapter<String> titlesAdapter = new ArrayAdapter<>(AddCategoryDialog.this.getActivity(), android.R.layout.simple_list_item_1, categoriesTitles);
        titlesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        titleSpinner.setAdapter(titlesAdapter);
        titleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                titleSpinner.setSelection(position);
                String title = titlesAdapter.getItem(position);
                if (title.equals("Other")) {
                    titleInput.setVisibility(View.VISIBLE);
                    validateCategoryTitle();
                } else {
                    titleInput.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CATEGORY_ICON_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                iconImageValue = data.getIntExtra("iconIntValue", DEFAULT_ICON);
                iconPrevView.setVisibility(View.VISIBLE);
                iconPrevView.setImageResource(iconImages[iconImageValue]);
                Log.d(TAG, "set icon image to value: " + Integer.toString(iconImageValue));
            }
        }
    }

    /**
     * validate the entered category title.
     */
    private void validateCategoryTitle() {
        setIsDocumentTitleValidToTrueIfValid();
        titleInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isCategoryTitleValid = false;
                int inputLength = titleInput.getEditText().getText().toString().length();
                if (inputLength >= MAX_CATEGORY_NAME_LENGTH) {
                    titleInput.setError("Maximum Limit Reached!");
                } else if (inputLength == 0) {
                    titleInput.setError("Category title is required!");
                } else {
                    titleInput.setError(null);
                    isCategoryTitleValid = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * set isDocumentTitleValid to true if document title is valid
     */
    private void setIsDocumentTitleValidToTrueIfValid() {
        int inputLength = titleInput.getEditText().getText().toString().length();
        if (inputLength < MAX_CATEGORY_NAME_LENGTH && inputLength > 0) {
            isCategoryTitleValid = true;
        }
    }

}