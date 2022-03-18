package com.example.saveit.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saveit.R;
import com.example.saveit.User.User;
import com.example.saveit.model.UserModel;

import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity {
    NavController navCtl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        NavHost navHost = (NavHost) getSupportFragmentManager().findFragmentById(R.id.login_navhost);
        navCtl = navHost.getNavController();

        NavigationUI.setupActionBarWithNavController(this, navCtl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.basic_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (!super.onOptionsItemSelected(item)) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    navCtl.navigateUp();
                    return true;
                default:
                    NavigationUI.onNavDestinationSelected(item, navCtl);
            }
        } else {
            return true;
        }
        return false;
    }
}
//    EditText userName, password, phoneNumber;
//    TextView allreadySignIn;
//    Button signUpBtn;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//
//        userName = findViewById(R.id.register_user_name_et);
//        password = findViewById(R.id.register_password_et);
//        phoneNumber = findViewById(R.id.register_phone_et);
//        allreadySignIn = findViewById(R.id.register_allready_sign_in_btn_tv);
//        signUpBtn = findViewById(R.id.register_sign_up_btn);
//
//        signUpBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CreateNewUser();
//            }
//        });
//
//        allreadySignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//    }
//
//    private void CreateNewUser() {
//        final String userNameTxt = userName.getText().toString();
//        final String passwordTxt = password.getText().toString();
//        final String phoneNumberTxt = phoneNumber.getText().toString();
//
//        User user = new User(userNameTxt,passwordTxt,phoneNumberTxt);
//        if (userNameTxt.isEmpty() || passwordTxt.isEmpty() || phoneNumberTxt.isEmpty()){
//            Toast.makeText(RegisterActivity.this, "All fields are rquierd", Toast.LENGTH_SHORT).show();
//        }else {
//            UserModel.instance.createUser(user, ()->{
//                Toast.makeText(RegisterActivity.this, "You are now part of SaveIT :)", Toast.LENGTH_SHORT).show();
//                finish();
//            });
//        }
//    }
