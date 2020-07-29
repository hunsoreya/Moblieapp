package com.example.readingshares.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.readingshares.R;
import com.example.readingshares.fragment.HomeFragment;
import com.example.readingshares.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText eTUsername, eTEmail, eTPassword, eTConfirmPw;
    private Button btnSignUp;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        //reference to Xml Layout
        eTUsername = (EditText) findViewById(R.id.txt_username);
        eTEmail = (EditText) findViewById(R.id.txt_email);
        eTPassword = (EditText) findViewById(R.id.txt_password);
        eTConfirmPw = (EditText) findViewById(R.id.txt_retype_password);
        btnSignUp = (Button) findViewById(R.id.btn_signUp);

        //getting Firebase auth Instance
        firebaseAuth = FirebaseAuth.getInstance();

        //Invoke the SignUp Button
        btnSignUp.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(firebaseAuth.getCurrentUser() != null) {
            //handle the already authenticated user
            startActivity(new Intent(getApplicationContext(), HomeFragment.class));
            finish();
        }
    }

    private void authenticatedUser() {

        final String username = eTUsername.getText().toString().trim();
        final String email = eTEmail.getText().toString().trim();
        String password = eTPassword.getText().toString().trim();
        String confirmPw = eTConfirmPw.getText().toString().trim();

        //validate
        if(username.isEmpty()) {
            eTUsername.setError(getString(R.string.input_error_username));
            eTUsername.requestFocus();
            return;
        }

        if(email.isEmpty()) {
            eTEmail.setError(getString(R.string.input_error_email));
            eTEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            eTEmail.setError(getString(R.string.input_error_email_invalid));
            eTEmail.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            eTPassword.setError(getString(R.string.input_error_password));
            eTPassword.requestFocus();
            return;
        }

        if(password.length() < 8) {
            eTPassword.setError(getString(R.string.input_error_password_length));
            eTPassword.requestFocus();
            return;
        }

        if(!confirmPw.equals(password)) {
            eTConfirmPw.setError(getString(R.string.input_error_pw_cPw));
            eTConfirmPw.requestFocus();
            return;
        }


        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()) {

                            //save user info
                            User user = new User(
                                    username,
                                    email
                            );

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    //progress bar loading delete

                                    if(task.isSuccessful()) {

                                        Toast.makeText(RegisterActivity.this, getString(R.string.registration_success), Toast.LENGTH_LONG).show();

                                        //loading splashing screen
                                        startActivity(new Intent(getApplicationContext(), LoadingActivity.class));
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                            }
                                        }, 2000);

                                    } else {
                                        Toast.makeText(RegisterActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                }
                            });

                        } else {
                            Toast.makeText(RegisterActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_signUp) {
            authenticatedUser();
        }

    }


}