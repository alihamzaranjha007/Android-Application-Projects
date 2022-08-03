package com.example.classwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewData extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Adopter mAdapter;
    private ProgressBar progressBarCircle;

    private DatabaseReference mDatabaseRef;
    private List<Upload> mUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_data);

        mRecyclerView= findViewById(R.id.recycleView);
        progressBarCircle= findViewById(R.id.progressBar_circle);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUpload= new ArrayList<>();
        mDatabaseRef= FirebaseDatabase.getInstance().getReference().child("Uploads");


        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUpload.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    Upload upload= postSnapshot.getValue(Upload.class);
                    mUpload.add(upload);
                }

                mAdapter= new Adopter(RecyclerViewData.this, mUpload);
                mRecyclerView.setAdapter(mAdapter);

                progressBarCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(RecyclerViewData.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBarCircle.setVisibility(View.INVISIBLE);
            }
        });
    }
}