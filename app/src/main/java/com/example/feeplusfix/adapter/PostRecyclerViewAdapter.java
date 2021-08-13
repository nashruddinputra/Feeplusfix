package com.example.feeplusfix.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feeplusfix.R;
import com.example.feeplusfix.model.Post;

import java.util.List;

public class PostRecyclerViewAdapter extends RecyclerView.Adapter<PostViewHolder> {

     List<Post> data;

    public PostRecyclerViewAdapter(List<Post> data){
        this.data = data;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

        holder.tvNamaBarang.setText(data.get(position).getNamaBarang());
        holder.tvDeskripsiBarang.setText(data.get(position).getDeskripsiBarang());
        holder.tvHargaBarang.setText(data.get(position).getHargaBarang());

    }

    @Override
    public int getItemCount() {
        return (data != null) ? data.size() : 0;
    }

}

