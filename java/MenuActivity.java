package com.example.classwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void transferFun(View view) {
        startActivity(new Intent(MenuActivity.this,MainActivity.class));
    }

    public void authFun(View view) {
        startActivity(new Intent(MenuActivity.this,LoginActivity.class));
    }

    public void recyclerFun(View view) {
        startActivity(new Intent(MenuActivity.this, AddData.class));
    }
}