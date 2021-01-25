package com.example.cinemaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class LoginActivity extends AppCompatActivity {

    EditText emailET,passwordET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        setTitle("Login Page");
        //Backendless.initApp(this,"106CEA6E-A846-7B0B-FF41-DEA5D3EB2B00","D4FA1D74-99CB-4C18-BE43-8C62D17D33E2");
        Backendless.initApp(this,"4192D836-B383-B50A-FF05-5E5F30870300","542D62D0-F6C7-4D5F-A32A-157220FFC408");
    }

    public void login(View view) {
        String mail=emailET.getText().toString();
        String pass=passwordET.getText().toString();
        Backendless.UserService.login(mail, pass, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                BackendlessUser user = new BackendlessUser();
                    Intent in = new Intent(LoginActivity.this,MainActivity.class);
                    emailET.setText("");
                    passwordET.setText("");
                    startActivity(in);
//                    Intent in = new Intent(LoginActivity.this,SettingsActivity.class);
//                    startActivity(in);

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(LoginActivity.this, "Wrong credentials.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void register(View view) {
        Intent in = new Intent(this,RegisterActivity.class);
        startActivity(in);
    }
}