package com.example.classwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText editText_email;
    private EditText editText_password;
    private Button login;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText_email = findViewById(R.id.editText_email_login);
        editText_password = findViewById(R.id.editTextTextPassword_login);
        login= findViewById(R.id.login_btn);
        progressBar= findViewById(R.id.progressBar);

        mAuth= FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_login();
            }
        });
        
        
    }

    private void user_login() {
        String email= editText_email.getText().toString().trim();
        String password= editText_password.getText().toString().trim();

        if (email.isEmpty()) {
            editText_email.setError("Enter email!");
            editText_email.requestFocus();
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editText_email.setError("Please valid email!");
            editText_email.requestFocus();
        }
        if (password.isEmpty()) {
            editText_password.setError("Enter password!");
            editText_password.requestFocus();
        }

        if (!(email.isEmpty() && password.isEmpty())){
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progressBar.setVisibility(View.INVISIBLE);
                        startActivity(new Intent(LoginActivity.this, userData.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "email or password is incorrect!", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
            });
        } else {
            Toast.makeText(LoginActivity.this, "wrong!", Toast.LENGTH_LONG).show();
        }
    }

    public void registerFun(View view) {
        startActivity(new Intent(LoginActivity.this, registerActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}