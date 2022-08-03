package com.example.classwork;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class registerActivity extends AppCompatActivity {

    ActivityResultLauncher<String> profileTakePhoto;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;
    private StorageReference mStorageRef;

    private EditText editText_name;
    private EditText editText_email;
    private EditText editText_password;
    private EditText editText_confirm;
    private CardView register;
    private ImageView  pImage;
    private RadioGroup gender_grp;
    private RadioButton gender_btn;
    private ProgressBar progressBar;

    private Uri imgUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        profileTakePhoto= registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        imgUri= result;
                        pImage.setImageURI(imgUri);
                    }
                }
        );



        editText_name= findViewById(R.id.editText_name);
        editText_email = findViewById(R.id.editText_email);
        editText_password = findViewById(R.id.editTextTextPassword);
        editText_confirm= findViewById(R.id.editTextConPass);
        register= findViewById(R.id.cardView_signup);
        gender_grp= findViewById(R.id.gender);
        pImage= findViewById(R.id.imageViewProfile);
        progressBar= findViewById(R.id.progressBar);

        mAuth= FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign_up();
            }
        });

        mStorageRef= FirebaseStorage.getInstance().getReference("profiles");
        mDatabaseRef= FirebaseDatabase.getInstance().getReference().child("users");

    }
    private void sign_up() {
        int selected_id= gender_grp.getCheckedRadioButtonId();
        gender_btn= findViewById(selected_id);

        String name = editText_name.getText().toString().trim();
        String email = editText_email.getText().toString().trim();
        String password = editText_password.getText().toString();
        String con_password = editText_confirm.getText().toString().trim();
        String gender= gender_btn.getText().toString();


        if (name.isEmpty()) {
            editText_name.setError("Enter name!");
            editText_name.requestFocus();
        }
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
        if (password.length() < 8) {
            editText_password.setError("Minimum length should be 8!");
            editText_password.requestFocus();
        }
        if (con_password.isEmpty()) {
            editText_confirm.setError("Enter phone!");
            editText_confirm.requestFocus();
        }
        if (!password.equals(con_password)) {
            editText_confirm.setError("Password not matched!");
            editText_confirm.requestFocus();
        }
        if (gender.isEmpty()) {
            Toast.makeText(this, "Please select gender!", Toast.LENGTH_SHORT).show();
        }
        if (imgUri==null){
            Toast.makeText(this, "Please select image!", Toast.LENGTH_SHORT).show();
        }
       progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                StorageReference fileRef= mStorageRef.child(mAuth.getCurrentUser().getUid())
                                        .child(System.currentTimeMillis()+".jpg");

                                fileRef.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                Users user= new Users(name, email, gender, uri.toString());

                                                mDatabaseRef.child(mAuth.getCurrentUser().getUid()).setValue(user)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()){
                                                                    progressBar.setVisibility(View.INVISIBLE);
                                                                    Toast.makeText(registerActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    Toast.makeText(registerActivity.this, "Failed to register try again!", Toast.LENGTH_LONG).show();
                                                                    progressBar.setVisibility(View.INVISIBLE);
                                                                }
                                                            }
                                                        });
                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(registerActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else {
                                Toast.makeText(registerActivity.this, "Failed try again!" + task.getException(), Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
    }

    public void choose_image(View view) {
        profileTakePhoto.launch("image/*");
    }

    public void loginFun(View view) {
        startActivity(new Intent(registerActivity.this, LoginActivity.class));
        finish();
    }
}