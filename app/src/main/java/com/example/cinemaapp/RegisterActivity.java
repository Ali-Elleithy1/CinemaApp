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
import com.backendless.persistence.DataQueryBuilder;

import java.util.List;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements AsyncCallback<BackendlessUser> {

    EditText fnameET,lnameET,nEmailET,nPasswordET,cPasswordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fnameET=findViewById(R.id.fnameET);
        lnameET=findViewById(R.id.lnameET);
        nEmailET=findViewById(R.id.nEmailET);
        nPasswordET=findViewById(R.id.nPasswordET);
        cPasswordET=findViewById(R.id.cPasswordET);
        setTitle("Register Page");
    }

    public boolean isRegistered(String mail)
    {
        boolean isR=true;
        BackendlessUser user = new BackendlessUser();

        //String currentUserId = Backendless.UserService.CurrentUser().getUserId();

        DataQueryBuilder builder=DataQueryBuilder.create();
        builder.setSortBy("email DESC");
        Backendless.Data.of(BackendlessUser.class).find(builder, new AsyncCallback<List<BackendlessUser>>() {
            @Override
            public void handleResponse(List<BackendlessUser> response) {
                if(response.size()>0) {
                    for(int i=0; i<response.size(); i++)
                    {
                        if(mail.equalsIgnoreCase(response.get(i).getEmail()))
                        {
                            Toast.makeText(RegisterActivity.this, "HERE", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(RegisterActivity.this, "problem loading transactions", Toast.LENGTH_SHORT).show();
            }
        });
        return false;
    }

    public void register(View view) {
        if(!fnameET.getText().toString().isEmpty() && !lnameET.getText().toString().isEmpty() && !nEmailET.getText().toString().isEmpty() && !nPasswordET.getText().toString().isEmpty()
                && !cPasswordET.getText().toString().isEmpty()) {
            isRegistered(nEmailET.getText().toString());

            if (nPasswordET.getText().toString().equals(cPasswordET.getText().toString())) {
                if (Pattern.matches("[a-zA-Z]+",fnameET.getText().toString())== true && Pattern.matches("[a-zA-Z]+",lnameET.getText().toString())== true) {
                    BackendlessUser user = new BackendlessUser();
                    user.setEmail(nEmailET.getText().toString());
                    user.setPassword(nPasswordET.getText().toString());
                    user.setProperty("fname", fnameET.getText().toString());
                    user.setProperty("lname", lnameET.getText().toString());

                    Backendless.UserService.register(user, this);
                } else {
                    Toast.makeText(this, "The name should only contain letters", Toast.LENGTH_SHORT).show();
                }
//            Clients client = new Clients();
//            client.setFname(fnameET.getText().toString());
//            client.setLname(lnameET.getText().toString());
//            client.setEmail(nEmailET.getText().toString());
//            client.setPassword(nPasswordET.getText().toString());
//
//            Backendless.Persistence.save(client, new AsyncCallback<Clients>() {
//                @Override
//                public void handleResponse(Clients response) {
//                    Toast.makeText(RegisterActivity.this, "Data Saved!", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void handleFault(BackendlessFault fault) {
//                    Toast.makeText(RegisterActivity.this, "problem creating new account", Toast.LENGTH_SHORT).show();
//                }
//            });

//            BackendlessUser user = new BackendlessUser();
//            user.setProperty("email", nEmailET.getText().toString());
//            user.setPassword(nPasswordET.getText().toString());
//            user.setProperty("fname",fnameET.getText().toString());
//            user.setProperty("lname",lnameET.getText().toString());


            } else {
                Toast.makeText(this, "Password is not matching.", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "Please fill out all the fields before submitting.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void handleResponse(BackendlessUser response) {
        Toast.makeText(this, "Data Saved!", Toast.LENGTH_SHORT).show();
        Intent in = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(in);
    }

    @Override
    public void handleFault(BackendlessFault fault) {
        if (fault.getCode().equals("3033")) {
            Toast.makeText(this, "user exists", Toast.LENGTH_SHORT).show();
        }
        else if(fault.getCode().equals("999"))
        {
            Toast.makeText(this, "Cannot process request - request per second limit has been exhausted", Toast.LENGTH_SHORT).show();
        }
        else if(fault.getCode().equals("1000"))
        {
            Toast.makeText(this, "Entity with the specified ID cannot be found", Toast.LENGTH_SHORT).show();
        }
        else if(fault.getCode().equals("1001"))
        {
            Toast.makeText(this, "Cannot update object without any properties", Toast.LENGTH_SHORT).show();
        }
        else if(fault.getCode().equals("1002"))
        {
            Toast.makeText(this, "Cannot process request, use User Service to create new user entities.", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "failed to register", Toast.LENGTH_SHORT).show();
        }

    }
}