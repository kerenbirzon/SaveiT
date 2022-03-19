package com.example.saveit.login;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saveit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class RegisterFragment extends Fragment {

    EditText userName, password, confirmPassword;
    TextView allreadySignUp, registerTitle;
    Button signUpBtn;
    FirebaseAuth SignUpmAuth;
    ProgressBar progressBar;
    String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        userName = view.findViewById(R.id.register_user_name_et);
        password = view.findViewById(R.id.register_password_et);
        confirmPassword = view.findViewById(R.id.register_password_confirm_et);
        allreadySignUp = view.findViewById(R.id.register_allready_sign_up_btn_tv);
        signUpBtn = view.findViewById(R.id.register_sign_up_btn);
        progressBar = view.findViewById(R.id.register_progressBar);
        SignUpmAuth = FirebaseAuth.getInstance();

        progressBar.setVisibility(View.INVISIBLE);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUserExists();
            }
        });

        allreadySignUp.setOnClickListener((v) -> {
            Navigation.findNavController(v).navigateUp();
        });


        return view;
    }

    public void createUser() {
        String inputUserName = userName.getText().toString();
        String inputPassword = password.getText().toString();
        String inputConfirmPassword = confirmPassword.getText().toString();

        if (!inputUserName.isEmpty() && !inputPassword.isEmpty() && (inputConfirmPassword.equals(inputPassword)) && (inputUserName.matches(emailPattern))){
            progressBar.setVisibility(View.VISIBLE);
            signUpBtn.setEnabled(false);

            SignUpmAuth.createUserWithEmailAndPassword(inputUserName,inputPassword)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(getContext(), "User created",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            signUpBtn.setEnabled(true);
                            resetValues();
                            if (SignUpmAuth.getCurrentUser() != null){
                                SignUpmAuth.signOut();
                            }
                            Navigation.findNavController(userName).navigateUp();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.INVISIBLE);
                            signUpBtn.setEnabled(true);
                            Toast.makeText(getContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
        }else {
            Toast.makeText(getContext(),"please fill the fields Correctly",Toast.LENGTH_SHORT).show();
        }
    }

    public void resetValues() {
        userName.setText("");
        password.setText("");
        confirmPassword.setText("");
    }

    public void checkUserExists() {
        String inputUserName = userName.getText().toString();
        if (SignUpmAuth != null && !inputUserName.isEmpty()){
            progressBar.setVisibility(View.VISIBLE);
            signUpBtn.setEnabled(false);
            SignUpmAuth.fetchSignInMethodsForEmail(inputUserName)
                    .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                            boolean checkResult = !task.getResult().getSignInMethods().isEmpty();
                            if (!checkResult){
                                createUser();
                            }else {
                                progressBar.setVisibility(View.INVISIBLE);
                                signUpBtn.setEnabled(true);
                                Toast.makeText(getContext(), "User already been created",Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.INVISIBLE);
                            signUpBtn.setEnabled(true);
                            Toast.makeText(getContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


}