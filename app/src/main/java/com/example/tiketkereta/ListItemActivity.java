package com.example.tiketkereta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ListItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
    }
}