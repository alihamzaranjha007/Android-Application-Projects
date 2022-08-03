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

public class AddData extends AppCompatActivity {

    private EditText editTextNameR;
    private EditText editTextAgeR;
    private EditText editTextPhoneR;
    private EditText editTextHeightR;
    private ProgressBar progressBarR;

    private Button upload;

    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        editTextNameR= findViewById(R.id.editText_name);
        editTextAgeR= findViewById(R.id.editText_age);
        editTextPhoneR= findViewById(R.id.editText_phone);
        editTextHeightR= findViewById(R.id.editText_height);
        progressBarR= findViewById(R.id.progressBar);
        upload= findViewById(R.id.saveBtn);

        mDatabaseRef= FirebaseDatabase.getInstance().getReference().child("Uploads");
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarR.setVisibility(View.VISIBLE);

                String name= editTextNameR.getText().toString().trim();
                String age= editTextAgeR.getText().toString().trim();
                String phone= editTextPhoneR.getText().toString().trim();
                String height= editTextHeightR.getText().toString().trim();

                Upload upload= new Upload(name,age,phone,height);
                String uploadId= mDatabaseRef.push().getKey();
                mDatabaseRef.child(uploadId).setValue(upload).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressBarR.setVisibility(View.INVISIBLE);
                        Toast.makeText(AddData.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBarR.setVisibility(View.INVISIBLE);
                        Toast.makeText(AddData.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
    }

    public void showFun(View view) {
        startActivity(new Intent(AddData.this, RecyclerViewData.class));
    }
}