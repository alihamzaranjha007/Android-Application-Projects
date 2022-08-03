package com.example.classwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewData extends AppCompatActivity {

    private DatabaseReference reference;
    private TextView textViewName;
    private TextView textViewAge;
    private TextView textViewPhone;
    private TextView textViewHeight;
    private TextView textViewMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        textViewName= findViewById(R.id.mName);
        textViewAge= findViewById(R.id.mAge);
        textViewPhone= findViewById(R.id.mPhone);
        textViewHeight= findViewById(R.id.mHeight);
        textViewMessage= findViewById(R.id.message);

        reference = FirebaseDatabase.getInstance().getReference().child("Members");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Upload upload= snapshot.child("member1").getValue(Upload.class);

                if (upload!=null){
                    Toast.makeText(ViewData.this, "Successfully retrieved!", Toast.LENGTH_SHORT).show();
                    String name= upload.getName();
                    String age= upload.getAge();
                    String phone= upload.getPhone();
                    String height= upload.getHeight();

                    textViewMessage.setText(name+"!");
                    textViewName.setText(name);
                    textViewAge.setText(age+" years");
                    textViewPhone.setText(phone);
                    textViewHeight.setText(height+" feet");
                } else {
                    Toast.makeText(ViewData.this, "Empty!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewData.this, "Failed try again!"+ error.toException(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}