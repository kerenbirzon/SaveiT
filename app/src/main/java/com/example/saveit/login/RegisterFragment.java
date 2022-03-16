package com.example.saveit.login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.saveit.R;
import com.example.saveit.User.User;
import com.example.saveit.main.Category;
import com.example.saveit.model.CategoryModel;
import com.example.saveit.model.UserModel;

public class RegisterFragment extends Fragment {

    EditText userName, password, phoneNumber;
    TextView allreadySignIn;
    Button signUpBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        userName = view.findViewById(R.id.register_user_name_et);
        password = view.findViewById(R.id.register_password_et);
        phoneNumber = view.findViewById(R.id.register_phone_et);
        allreadySignIn = view.findViewById(R.id.register_allready_sign_in_btn_tv);
        signUpBtn = view.findViewById(R.id.register_sign_up_btn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveUser();
            }
        });

        allreadySignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        return view;
    }

    private void SaveUser() {
        User user = new User();
        user.setUserName(userName.getText().toString());
        user.setPassword(password.getText().toString());
        user.setPhoneNumber(phoneNumber.getText().toString());

        Log.d("TAG","saved userName:" + userName +" password:" + password + " phoneNumber" + phoneNumber);
        UserModel.instance.createUser(user,()->{
            Navigation.findNavController(userName).navigateUp();
        });
    }
}