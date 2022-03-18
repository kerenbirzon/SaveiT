package com.example.saveit.login;

import android.app.ProgressDialog;
import android.content.Intent;
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

import com.example.saveit.MainActivity;
import com.example.saveit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    EditText userName, password;
    Button loginBtn;
    TextView RegisterationBtn, loginTitle;
    FirebaseAuth signInmAuth;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container,false);

        userName = view.findViewById(R.id.login_user_name_et);
        password = view.findViewById(R.id.login_password_et);
        loginBtn = view.findViewById(R.id.login_login_btn);
        RegisterationBtn = view.findViewById(R.id.login_sign_up_btn_tv);
        loginBtn = view.findViewById(R.id.login_login_btn);

        progressBar = view.findViewById(R.id.login_progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        signInmAuth = FirebaseAuth.getInstance();
        RegisterationBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_registerFragment));

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInUser();
            }
        });

        return view;
    }

    public void signInUser() {
        String inputUserName = userName.getText().toString();
        String inputPassword = password.getText().toString();

        if (!inputUserName.isEmpty() && !inputPassword.isEmpty()) {
            if (signInmAuth != null){
                progressBar.setVisibility(View.VISIBLE);
                loginBtn.setEnabled(false);

                signInmAuth.signInWithEmailAndPassword(inputUserName,inputPassword)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
                                progressBar.setVisibility(View.VISIBLE);
                                loginBtn.setEnabled(true);
                                getActivity().finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressBar.setVisibility(View.INVISIBLE);
                                loginBtn.setEnabled(true);
                                Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }

        }else {
            Toast.makeText(getContext(),"please fill the fields Correctly",Toast.LENGTH_SHORT).show();
        }
    }
}