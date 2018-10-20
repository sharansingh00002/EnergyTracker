package com.example.sharansingh.solarifyenergy;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    ArrayList<String> challenge;
    Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        ImageView imageView;
        Button button;

        public MyViewHolder(View itemView) {
            super(itemView);

            mTextView = itemView.findViewById(R.id.challenge_name);
            imageView = itemView.findViewById(R.id.image);
            button = itemView.findViewById(R.id.enter);
        }
    }

    public CustomAdapter(Context context, ArrayList<String> challenge) {
        this.context = context;
        this.challenge = challenge;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row, parent, false);
// set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.mTextView.setText(challenge.get(position));
        holder.button.setText("Enter");
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StartChallenge.class);
                intent.putExtra("Challenge_name",challenge.get(position));
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"PRESS THE BUTTON TO KNOW MORE ABOUT THE CHALLENGE",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return challenge.size();
    }

}

