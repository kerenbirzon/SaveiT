package com.example.saveit.main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.saveit.R;
import com.example.saveit.model.Category;
import com.example.saveit.model.CategoryModel;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class AddCategoryFragment extends Fragment {
    private static final int REQUEST_CAMERA = 1;
    public static int PICK_IMAGE_REQUEST = 2;
    private EditText categoryNameEt, documentNameEt, documentTypeEt, documentCommentsEt;
    private Button saveNewCategoryBtn, cancelNewCategoryBtn;
    private ImageButton cameraBtn, galleryBtn;
    private ImageView documentImagePrev;
    private ProgressBar progressBar;
    private Category editCategory;
    Bitmap imageBitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_category, container, false);
        saveNewCategoryBtn = view.findViewById(R.id.btn_change_action_save);
        cancelNewCategoryBtn = view.findViewById(R.id.btn_change_action_cancel);
        cameraBtn = view.findViewById(R.id.camera_btn);
        galleryBtn = view.findViewById(R.id.gallery_btn);
        documentImagePrev = view.findViewById(R.id.document_photo_prev);
        categoryNameEt = view.findViewById(R.id.add_Category_name_et);
        documentNameEt = view.findViewById(R.id.document_name_et);
        documentTypeEt = view.findViewById(R.id.document_type_et);
        documentCommentsEt = view.findViewById(R.id.document_comment_et);
        progressBar = view.findViewById(R.id.change_category_progressBar);
        progressBar.setVisibility(View.GONE);

        cameraBtn.setOnClickListener(v -> openCam());

        galleryBtn.setOnClickListener(v -> openGallery());
        if (editCategory != null) {
            ((TextView) view.findViewById(R.id.main_title)).setText("Edit Category");
            categoryNameEt.setText(editCategory.getCategoryTitle());
            documentNameEt.setText(editCategory.getDocumentTitle());
            documentTypeEt.setText(editCategory.getDocumentType());
            documentCommentsEt.setText(editCategory.getDocumentComments());
            Picasso.get().load(editCategory.getImageUrl()).into(documentImagePrev);
        }

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

        return view;
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void openCam() {
        if (getContext() == null || getActivity() == null) return;
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 5);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CAMERA);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null & getActivity() != null) {
                    this.imageBitmap = (Bitmap) data.getExtras().get("data");
                    documentImagePrev.setImageBitmap(imageBitmap);
                }
            }
        } else if (requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {

                if (data != null & getActivity() != null) {
                    Uri imageUri = data.getData();
                    documentImagePrev.setImageURI(imageUri);

                    try {
                        this.imageBitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), imageUri);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }

    private void saveCategory() {
        if (isValid()) {
            progressBar.setVisibility(View.VISIBLE);
            saveNewCategoryBtn.setEnabled(false);
            cancelNewCategoryBtn.setEnabled(false);
            galleryBtn.setEnabled(false);
            cameraBtn.setEnabled(false);
            String categoryTitle = categoryNameEt.getText().toString();
            String documentTitle = documentNameEt.getText().toString();
            String documentType = documentTypeEt.getText().toString();
            String documentComments = documentCommentsEt.getText().toString();
            Category category = new Category(categoryTitle,documentTitle,"",documentType,documentComments);
//        CategoryModel.instance.addCategory(category,()->{
//            Navigation.findNavController(categoryNameEt).navigateUp();
//        });
            if(editCategory != null){
                category.setId(editCategory.getId());
                category.setImageUrl(editCategory.getImageUrl());
                CategoryModel.instance.editCategory(category, () -> Navigation.findNavController(categoryNameEt).navigateUp());
            } else {
                CategoryModel.instance.saveImage(imageBitmap, UUID.randomUUID() + ".jpg", url -> {
                    category.setImageUrl(url);
                    CategoryModel.instance.addCategory(category, () -> Navigation.findNavController(categoryNameEt).navigateUp());
                });
            }
        }
    }

    private boolean isValid() {
        if (imageBitmap == null && editCategory == null) {
            Toast.makeText(getContext(), "You Must Select Image", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(categoryNameEt.getText().toString()) || TextUtils.isEmpty(documentNameEt.getText().toString()) ||
                TextUtils.isEmpty(documentTypeEt.getText().toString()) ) {
            Toast.makeText(getContext(), "All fields must not be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}