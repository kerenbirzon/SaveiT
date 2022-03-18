package com.example.saveit.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.saveit.MainActivity;
import com.example.saveit.R;

public class LoginFragment extends Fragment {

    EditText userName, password;
    Button loginBtn;
    TextView RegisterationBtn, loginTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container,false);

        userName = view.findViewById(R.id.login_user_name_et);
        password = view.findViewById(R.id.login_password_et);
        loginBtn = view.findViewById(R.id.login_login_btn);
        RegisterationBtn = view.findViewById(R.id.login_sign_up_btn_tv);
        loginTitle = view.findViewById(R.id.login_title_tv);

        RegisterationBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_registerFragment));

        Button loginBtn = view.findViewById(R.id.login_login_btn);
        loginBtn.setOnClickListener(v -> {
            //TODO - connect to model login function
            toFeedActivity();
        });
        return view;
    }

    private void toFeedActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}