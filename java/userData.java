package com.example.classwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class userData extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String UserID;

    private TextView textViewName;
    private TextView textViewEmail;
    private TextView textViewGender;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        textViewName= findViewById(R.id.Name_empty);
        textViewEmail= findViewById(R.id.Email_empty);
        textViewGender= findViewById(R.id.gender);
        imageView= findViewById(R.id.imageView2);

        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        UserID= user.getUid();

        reference= FirebaseDatabase.getInstance().getReference().child("users").child(UserID);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);

                if (user!=null){
                    String profile= user.getUri();
                    String name= user.getName();
                    String email= user.getEmail();
                    String gender= user.getGender();

                    textViewName.setText(name);
                    textViewEmail.setText(email);
                    textViewGender.setText(gender);
                    Picasso.get()
                            .load(profile)
                            .into(imageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(userData.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}