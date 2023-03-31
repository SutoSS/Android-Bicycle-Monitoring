package com.example.monitoringsepedaapps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.protobuf.StringValue;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LeaderboardAdapter  extends RecyclerView.Adapter<LeaderboardAdapter.MyViewHolder>{

    Context context;
    ArrayList<LeaderboardList> userArrayList;
    int i = 1;

    public LeaderboardAdapter(Context context, ArrayList<LeaderboardList> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public LeaderboardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_menu_leaderboard,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardAdapter.MyViewHolder holder, int position) {
        LeaderboardList leaderboardList= userArrayList.get(position);
        holder.nama.setText(leaderboardList.nama);
        holder.level.setText(leaderboardList.level);
        holder.poin.setText(leaderboardList.poin);
        holder.rank.setText(String.valueOf(i));
        i++;

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nama, level, poin, email, no_telfon, uid, rank;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.tv_nama_ldb);
            level = itemView.findViewById(R.id.tv_level_ldb);
            poin = itemView.findViewById(R.id.tv_poin_ldb);
            rank = itemView.findViewById(R.id.tv_rank_ldb);
        }
    }

//    @NonNull
//    @Override
//    public LeaderboardAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        View v = LayoutInflater.from(context).inflate(R.layout.list_menu_leaderboard,parent,false);
//        myViewHolder viewHolder = new myViewHolder(v);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull LeaderboardAdapter.myViewHolder holder, @SuppressLint("RecyclerView") int position) {
//
//        holder.nama.setText(userArrayList.get(position).getNama());
//        holder.level.setText(userArrayList.get(position).getLevel());
//        holder.poin.setText(userArrayList.get(position).getPoin());
//    }
//
//    @Override
//    public int getItemCount() {
//        return userArrayList.size();
//    }
//
//    public static class myViewHolder extends RecyclerView.ViewHolder{
//
//        TextView nama, level, poin, email, no_telfon, uid;
//
//        public myViewHolder(@NonNull View itemView) {
//            super(itemView);
//            nama = itemView.findViewById(R.id.tv_nama_ldb);
//            level = itemView.findViewById(R.id.tv_level_ldb);
//            poin = itemView.findViewById(R.id.tv_poin_ldb);
//
//        }
//    }
}
