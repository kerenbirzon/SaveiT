package com.example.saveit.document;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.saveit.R;
import com.example.saveit.category.Document;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DocumentActivity extends AppCompatActivity {
    private static final String TAG = "DocumentActivity";
    private static final long ONE_MEGABYTE = 1024 * 1024;
    public static final int FILE_REQUEST_CODE = 10;
    private static int notificationId = 0;
    private TextInputLayout documentTitleET;
    private TextInputLayout documentCommentET;
    private TextInputLayout documentExpirationDateET;
    private TextInputLayout reminderTimeET1;
    private String callReason;
    private String categoryTitle;
    private int position;
    private Spinner reminderSpinner1;
    private SwitchMaterial reminderSwitch;
    private Document curDocument = new Document();
    private Button addImageBtn, addFileBtn;
    private Uri selectedImage, fileUri, fileDownloadUri;
    private ImageView documentImageView;
    private LinearLayout filePreviewLayout;
    private ProgressBar progressBar;
    private boolean changedImage;
    private boolean changedReminderTime = false;
    private boolean isAlarm = false;
    private CheckBox check1;
    private TimePicker alarmTimePicker1;
    private DatePicker datePicker;
    private int alarmBeforeDay = 0;
    private long alarmBeforeWeek = 0;
    private int alarmBeforeMonth = 0;

    private View v1;
    private View v4;
    private View v5;
    private View v6;

    private boolean isDocumentTitleValid = false;
    private static StorageReference storageReference;
    private FirebaseStorage storage;
    private boolean isUploadingFile = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        initializeActivityFields();
        filePreviewLayout.setVisibility(View.GONE);
        setReminderTime();

    }

    private void setReminderTime() {
        final ArrayAdapter<String> titlesAdapter = new ArrayAdapter<>(DocumentActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.times));
        titlesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reminderSpinner1.setAdapter(titlesAdapter);
        reminderSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                reminderSpinner1.setSelection(position);
                String title = titlesAdapter.getItem(position);

                switch (title) {
                    case "day before":
                        alarmBeforeDay = 1;
                        break;
                    case "week before":
                        alarmBeforeWeek = 604800000;
                        break;
                    case "month before":
                        alarmBeforeMonth = 1;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    /*
     * This method initializes Activity view Fields.
     */
    private void initializeActivityFields() {
        documentTitleET = findViewById(R.id.et_document_title);
        documentCommentET = findViewById(R.id.et_comment);
        reminderTimeET1 = findViewById(R.id.et_time1);
        reminderSwitch = findViewById(R.id.add_alarm);
        reminderSpinner1 = findViewById(R.id.spinner_times1);
        check1 = findViewById(R.id.checkbox_calendar1);
        documentExpirationDateET = findViewById(R.id.et_expiration_date);
        documentImageView = findViewById(R.id.iv_doc);
        filePreviewLayout = findViewById(R.id.ll_doc_file_prev);
        progressBar = findViewById(R.id.fileProgressBar);
        progressBar.setVisibility(View.INVISIBLE);
        addImageBtn = findViewById(R.id.btn_add_doc_image);
        addFileBtn = findViewById(R.id.btn_add_doc_file);

        v1 = findViewById(R.id.et_time1);
        v4 = findViewById(R.id.tv_add_to_calendar1);
        v5 = findViewById(R.id.checkbox_calendar1);
        v6 = findViewById(R.id.spinner_times1);
    }

}