package com.example.monitoringsepedaapps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.myViewHolder> {
    Context context;
    ArrayList<EducationList> isiEduList;

    public EducationAdapter(Context context, ArrayList<EducationList> isiEduList) {
        this.context = context;
        this.isiEduList = isiEduList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_menu_education, parent, false);
        myViewHolder viewHolder = new myViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.judul.setText(isiEduList.get(position).getJudul());
        holder.deskripsi.setText(isiEduList.get(position).getDeskripsi());
//        holder.gambar.setImageResource(isiEduList.get(position).getImage());
//        Picasso.get().load(isiEduList.get(position).getImage()).into(holder.gambar);
        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DeskripsiEduActivity.class);
                intent.putExtra("judul", isiEduList.get(position).getJudul());
                intent.putExtra("deskripsi", isiEduList.get(position).getDeskripsi());
//                intent.putExtra("gambar", isiEduList.get(position).getImage());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return isiEduList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        private TextView judul, deskripsi;
//        private ImageView gambar;
        private LinearLayout menu;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.tV_judul);
            deskripsi = itemView.findViewById(R.id.tV_penjelasan);
//            gambar = itemView.findViewById(R.id.img_edu);
            menu = itemView.findViewById(R.id.LL_menu);


        }
    }
}
