package com.example.readingshares.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.readingshares.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText resetEmail;
    private Button btnResetEmail;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        //reference to xml layout
        resetEmail = (EditText) findViewById(R.id.reset_email);
        btnResetEmail = (Button) findViewById(R.id.btn_reset_email);

        //Initialize firebase
        firebaseAuth = FirebaseAuth.getInstance();

        btnResetEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = resetEmail.getText().toString().trim();

                if(email.isEmpty()) {
                    resetEmail.setError(getString(R.string.input_error_email));
                    resetEmail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    resetEmail.setError(getString(R.string.input_error_email_invalid));
                    resetEmail.requestFocus();
                    return;
                }
                
                firebaseAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                
                                if(task.isSuccessful()) {
                                    Toast.makeText(ForgetPasswordActivity.this, "Check email", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ForgetPasswordActivity.this, "Email not match", Toast.LENGTH_SHORT).show();
                                }
                                
                            }
                        });

            }
        });

    }

}