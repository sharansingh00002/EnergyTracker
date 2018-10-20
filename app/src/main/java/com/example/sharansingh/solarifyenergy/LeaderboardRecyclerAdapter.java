package com.example.sharansingh.solarifyenergy;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class LeaderboardRecyclerAdapter extends RecyclerView.Adapter<LeaderboardRecyclerAdapter.LeaderboardViewHolder>{

    Context context;
    int count=1;
    int sizeValue=0;
    ArrayList<LeaderBoardDataType> leaderBoardlist;
    public LeaderboardRecyclerAdapter(Context context,ArrayList<LeaderBoardDataType> leaderBoardDataType) {
        leaderBoardlist=leaderBoardDataType;
        this.context = context;
        sizeValue=leaderBoardlist.size();
    }

    @Override
    public LeaderboardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.leaderboard_recycler_view_layout,parent,false);
        return new LeaderboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LeaderboardViewHolder holder, int position) {
    LeaderBoardDataType temp=leaderBoardlist.get(position);
    holder.number.setText(""+(position+1));
    holder.name.setText(temp.getName());
    holder.value.setText(temp.getValue());

        Log.i("leaderboardrev",position+" "+sizeValue);

    if(position<(sizeValue/3))
    {
       Log.i("sizevvalues",(sizeValue/3)+" "+((sizeValue/3)*2)+" ");
        holder.linearLayout.setBackgroundColor(Color.parseColor("#2CE73C"));}
    else if(position<((sizeValue/3)*2))
    {
       // Log.i("leaderboardrev",position+"");
        holder.linearLayout.setBackgroundColor(Color.parseColor("#F4FA58"));}
    else
    {
        holder.linearLayout.setBackgroundColor(Color.parseColor("#FE2E2E"));
    }

    }



    @Override
    public int getItemCount() {
        sizeValue=leaderBoardlist.size();
        return leaderBoardlist.size();
    }

    public class LeaderboardViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,value,number;
        LinearLayout linearLayout;
        public LeaderboardViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.leaderboard_name);
            value=(TextView)itemView.findViewById(R.id.leaderboard_value);
            number=(TextView)itemView.findViewById(R.id.leaderboard_count);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.linearlayoutRV);
        }
    }
}
