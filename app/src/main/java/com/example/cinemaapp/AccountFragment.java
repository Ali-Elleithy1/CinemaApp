package com.example.cinemaapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.backendless.Backendless;

public class AccountFragment extends Fragment {
    EditText nameET;


    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        nameET=view.findViewById(R.id.nameET);
        nameET.setEnabled(false);

        String currentUserFName = Backendless.UserService.CurrentUser().getProperty("fname").toString();
        String currentUserLName = Backendless.UserService.CurrentUser().getProperty("lname").toString();
        nameET.setText(currentUserFName+" "+currentUserLName);

        return view;
    }
}