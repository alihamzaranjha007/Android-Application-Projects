package com.example.classwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextAge;
    private EditText editTextPhone;
    private EditText editTextHeight;
    private ProgressBar progressBar;

    private Button save;

    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName= findViewById(R.id.editText_name);
        editTextAge= findViewById(R.id.editText_age);
        editTextPhone= findViewById(R.id.editText_phone);
        editTextHeight= findViewById(R.id.editText_height);
        progressBar= findViewById(R.id.progressBar);
        save= findViewById(R.id.saveBtn);


        mDatabaseRef= FirebaseDatabase.getInstance().getReference().child("Members");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                String name= editTextName.getText().toString().trim();
                String age= editTextAge.getText().toString().trim();
                String phone= editTextPhone.getText().toString().trim();
                String height= editTextHeight.getText().toString().trim();

                Upload upload= new Upload(name,age,phone,height);
                mDatabaseRef.child("member1").setValue(upload).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(MainActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    public void showFun(View view) {
        startActivity(new Intent(MainActivity.this, ViewData.class));
    }
}