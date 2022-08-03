package com.example.classwork;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Adopter extends RecyclerView.Adapter<Adopter.imageViewHolder>{

    private Context mContext;
    private List<Upload> mUpload;

    public Adopter(Context context, List<Upload> uploads){
        mContext= context;
        this.mUpload= uploads;
    }

    @Override
    public imageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.items, parent, false);
        return new imageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull imageViewHolder holder, int position) {
        Upload uploadCurrent= mUpload.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        holder.textViewAge.setText(uploadCurrent.getAge());

    }

    @Override
    public int getItemCount() {
        return mUpload.size();
    }

    public class imageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewAge;



        public imageViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName= itemView.findViewById(R.id.NameR);
            textViewAge= itemView.findViewById(R.id.EmailR);

        }

    }
}
