package com.example.saveit.category;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saveit.MainActivity;
import com.example.saveit.R;
import com.example.saveit.document.DocumentActivity;
import com.example.saveit.main.CategoryListFragmentDirections;
import com.example.saveit.document.DocumentActivity;
import com.example.saveit.model.AppLocalDb;
import com.example.saveit.model.Category;
import com.example.saveit.model.CategoryModel;
import com.example.saveit.model.Document;
import com.example.saveit.model.DocumentModel;
import com.example.saveit.model.ModelFirebase;
//import com.example.saveit.model.Document;

import java.util.List;

public class CategoryFragment extends Fragment {

    private List<Document> documentList;
    private ImageView docImg;
    private TextView noDocTxt, titleTxt;
    private String categoryTitle;
    Button addDocumentBtn;
    public static final int NEW_DOCUMENT = 111;
    NavController navController;
    DocumentAdapter documentAdapter;
    public static final int EDIT_DOCUMENT = 222;
    public static final int DEFAULT_POSITION_VALUE = -1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category, container, false);

        categoryTitle = CategoryFragmentArgs.fromBundle(getArguments()).getCategory().getTitle();

        CategoryModel.instance.getCategoryByTitle(categoryTitle, new CategoryModel.GetCategoryByTitle() {
            @Override
            public void OnComplete(Category category) {
                titleTxt.setText(categoryTitle);
            }
        });

        navController = NavHostFragment.findNavController(this);
        titleTxt = view.findViewById(R.id.tv_category_title);
        docImg = view.findViewById(R.id.iv_doc_img);
        noDocTxt = view.findViewById(R.id.tv_no_docs);

        addDocumentBtn = view.findViewById(R.id.btn_add_doc);
        addDocumentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), DocumentActivity.class);
                intent.putExtra("call_reason", "new_document");
                intent.putExtra("category_title", categoryTitle);
                startActivityForResult(intent, NEW_DOCUMENT);
                //Navigation.createNavigateOnClickListener(R.id.action_category_to_document);
            }
        });

        documentList = DocumentModel.instance.getDocuments(categoryTitle);

        RecyclerView recyclerView = view.findViewById(R.id.document_recycler);
        recyclerView.hasFixedSize();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        documentAdapter = new DocumentAdapter(documentList);
        recyclerView.setAdapter(documentAdapter);

        documentAdapter.setDocumentClickListener(new DocumentClickListener() {
            @Override
            public void onDocumentClicked(int position) {
                Log.d("document clicked", "document was clicked");
            }
        });

        setHasOptionsMenu(true);

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_DOCUMENT) {
            //add new document
            if (resultCode == RESULT_OK) {
                addDocument(data);
                if (documentList.size() > 0) {
                    docImg.setVisibility(View.GONE);
                    noDocTxt.setVisibility(View.GONE);
                }
            }
        }
        if (requestCode == EDIT_DOCUMENT) {
            //edit existing document
            if (resultCode == RESULT_OK) {
                updateDocument(data);
            }
        }
    }

    /*
    the method adds a document to a category
     */
    private void addDocument(Intent data) {
        String title = data.getStringExtra("document_title");
        String comment = data.getStringExtra("document_comment");
        String expirationDate = data.getStringExtra("document_expiration_date");
        String reminderTime = data.getStringExtra("document_reminder_time");
        boolean hasAlarm = data.getBooleanExtra("is_alarm", false);
        boolean isAddEventToPhoneCalender = data.getBooleanExtra("is_add_event_to_phone_calender", false);
        boolean hasImage = data.getBooleanExtra("has_image", false);
        String fileDownloadUriStr = data.getStringExtra("file_download_uri");
        boolean hasFile = data.getBooleanExtra("has_file", false);
        Log.e("TAG", "adding new document " + title);
        Document newDocument = new Document(title, comment, expirationDate, null, hasImage, hasAlarm, hasFile, fileDownloadUriStr, reminderTime, isAddEventToPhoneCalender);
        documentList.add(newDocument);
        ModelFirebase.addNewDocument(categoryTitle, newDocument);
        documentAdapter.notifyItemInserted(documentList.size() - 1);
    }

    /*
    the method updates the document fields (also in the fireStore)
     */
    private void updateDocument(Intent data) {
        String title = data.getStringExtra("document_title");
        String comment = data.getStringExtra("document_comment");
        String expirationDate = data.getStringExtra("document_expiration_date");
        String reminderTime = data.getStringExtra("document_reminder_time");
        int position = data.getIntExtra("document_position", DEFAULT_POSITION_VALUE);
        boolean hasAlarm = data.getBooleanExtra("is_alarm", false);
        boolean isAddEventToPhoneCalender = data.getBooleanExtra("is_add_event_to_phone_calender", false);
        boolean hasImage = data.getBooleanExtra("has_image", false);
        String fileDownloadUriStr = data.getStringExtra("file_download_uri");
        boolean hasFile = data.getBooleanExtra("has_file", false);
        Log.e("TAG", "editing document " + title);
        Document document = documentList.get(position);
        ModelFirebase.removeDocument(categoryTitle, document);
        if (!title.equals(document.getTitle())) {
            document.setTitle(title);
        }
        if (!comment.equals(document.getComment())) {
            document.setComment(comment);
        }
        if (!expirationDate.equals(document.getExpirationDate())) {
            document.setExpirationDate(expirationDate);
        }
        document.setReminderTime(reminderTime);
        document.setHasFile(hasFile);
        document.setHasAlarm(hasAlarm);
        document.setIsAddEventToPhoneCalender(isAddEventToPhoneCalender);
        document.setFileDownloadUri(fileDownloadUriStr);
        document.setHasImage(hasImage);
        ModelFirebase.addNewDocument(categoryTitle, document);
        documentAdapter.notifyDataSetChanged();
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.category_list_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.category_delete:
                addDocumentBtn.setEnabled(false);
                new Thread(() -> {
                    Category category = CategoryFragmentArgs.fromBundle(getArguments()).getCategory();
                    AppLocalDb.db.categoryDao().delete(category);
                    CategoryModel.instance.deleteCategory(category, () -> navController.navigateUp());
                }).start();
                break;
            case R.id.category_edit:
                //navController.navigate(R.id.action_categoryFragment_to_editCategoryFragment);
                navController.navigate(CategoryFragmentDirections.actionCategoryFragmentToEditCategoryFragment(CategoryFragmentArgs.fromBundle(getArguments()).getCategory()));
                break;
        }
        return true;
    }
}