package com.example.readingshares.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.readingshares.R;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

    }
    public void log(View view){
        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
        startActivity(intent);
    }
    public void create(View view){
        Intent intent = new Intent(LogInActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
    public void forget(View view){
        Intent intent = new Intent(LogInActivity.this,ForgetPasswordActivity.class);
        startActivity(intent);
    }
}