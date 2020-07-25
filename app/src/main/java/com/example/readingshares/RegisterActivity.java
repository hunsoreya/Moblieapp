package com.example.readingshares;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    }
    public void create(View view){
        Intent intent = new Intent(RegisterActivity.this,LogInActivity.class);
        startActivity(intent);
    }
}