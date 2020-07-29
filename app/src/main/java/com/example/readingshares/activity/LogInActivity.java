package com.example.readingshares.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.readingshares.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private final int RC_SIGN_IN = 1;

    private TextView btn_forget_pw, btn_create_account;
    private EditText txt_login_email, txt_login_pw;
    private Button btn_signIn, btn_google_sign_in;

    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //Initializing
        btn_forget_pw = (TextView) findViewById(R.id.btn_forget_pw);
        btn_create_account = (TextView) findViewById(R.id.btn_create_account);
        btn_signIn = (Button) findViewById(R.id.btn_signIn);
        btn_google_sign_in = (Button) findViewById(R.id.btn_google_sign_in);

        txt_login_email = (EditText) findViewById(R.id.txt_login_email);
        txt_login_pw = (EditText) findViewById(R.id.txt_login_password);

        //getting Firebase auth Instance
        firebaseAuth = FirebaseAuth.getInstance();

        //signIn with google
        configGoogleSignIn();


        btn_signIn.setOnClickListener(this);
        btn_forget_pw.setOnClickListener(this);
        btn_create_account.setOnClickListener(this);
        btn_google_sign_in.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(firebaseAuth.getCurrentUser() != null) {
            //handle the already authenticated user
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        }
    }

    private void signIn() {

        String email = txt_login_email.getText().toString().trim();
        String password = txt_login_pw.getText().toString().trim();

        if(email.isEmpty()) {
            txt_login_email.setError(getString(R.string.input_error_email));
            txt_login_email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txt_login_email.setError(getString(R.string.input_error_email_invalid));
            txt_login_email.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            txt_login_pw.setError(getString(R.string.input_error_password));
            txt_login_pw.requestFocus();
            return;
        }

        if(password.length() < 8) {
            txt_login_pw.setError(getString(R.string.input_error_password_length));
            txt_login_pw.requestFocus();
            return;
        }

        //authenticate the user
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {

                    Toast.makeText(LogInActivity.this,"Login successfully", Toast.LENGTH_SHORT).show();

                    //loading splashing screen
                    startActivity(new Intent(getApplicationContext(), LoadingActivity.class));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        }
                    }, 2000);

                } else {

                    Toast.makeText(LogInActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void configGoogleSignIn() {

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

    }

    public void signInWithGoogle() {

        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    public void openForgetScreen(){

        Intent intent = new Intent(LogInActivity.this,ForgetPasswordActivity.class);
        startActivity(intent);

    }

    private void openSignInScreen() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completeTask) {

        try{

            GoogleSignInAccount googleSignInAccount = completeTask.getResult(ApiException.class);
            Toast.makeText(LogInActivity.this, "successfully sign in", Toast.LENGTH_SHORT).show();

            firebaseGoogleAuth(googleSignInAccount);

        } catch (ApiException e) {
            e.printStackTrace();
            firebaseGoogleAuth(null);
        }

    }

    private void firebaseGoogleAuth(GoogleSignInAccount googleSignInAccount) {

        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
        firebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()) {

                            Toast.makeText(LogInActivity.this, "login successfully", Toast.LENGTH_SHORT).show();
                            //going to homepage
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));

                        } else {
                            Toast.makeText(LogInActivity.this, "login successfully", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_forget_pw:
                openForgetScreen();
                break;

            case R.id.btn_create_account:
                openSignInScreen();
                break;

            case R.id.btn_signIn:
                signIn();
                break;

            case R.id.btn_google_sign_in:
                signInWithGoogle();
                break;
        }

    }

}