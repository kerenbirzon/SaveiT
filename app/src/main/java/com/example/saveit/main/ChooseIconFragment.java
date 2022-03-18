package com.example.saveit.main;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.example.saveit.R;

public class ChooseIconFragment extends Fragment {
    private Drawable highlight;
    private int lastImageToBeSelected = 0;
    private ImageView lastImage = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_icon, container, false);
        highlight = getResources().getDrawable(R.drawable.icon_selection);
        //setButtonsOnClickMethods(view);

        GridLayout grid = view.findViewById(R.id.gl_choose_icon);
        int childCount = grid.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final ImageView currentImage = (ImageView) grid.getChildAt(i);
            final int finalI = i;
            currentImage.setOnClickListener(new ImageView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lastImage != null) {
                        lastImage.setBackground(null);
                    }
                    lastImage = currentImage;
                    lastImageToBeSelected = finalI;
                    currentImage.setBackground(highlight);
                }
            });
        }

        Button cancelIconBtn = view.findViewById(R.id.btn_action_cancel);
        Button saveIconBtn = view.findViewById(R.id.btn_save_category_icon);

        saveIconBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("clicked", "actionSaveButton");

                //Intent intent = new Intent(ChooseIconFragment.this.getContext(),AddCategoryFragment.class);
                //intent.putExtra("iconIntValue", lastImageToBeSelected);

                //Navigation.findNavController(view).navigate(ChooseIconFragmentDirections.actionChooseIconFragmentPop());
                // need to continue here!!!!!!
            }
        });

        cancelIconBtn.setOnClickListener((v) -> {
            Navigation.findNavController(v).navigateUp();
        });

        return view;
    }

    public void show(FragmentManager fragmentManager, String dialog) {
    }
}