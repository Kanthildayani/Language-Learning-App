package com.example.lla;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.VerticalViewHolder> {
    private List<HorizontalDataModel> dataList;

    public VerticalAdapter(List<HorizontalDataModel> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public VerticalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vertical_item_layout, parent, false);
        return new VerticalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VerticalViewHolder holder, int position) {
        HorizontalDataModel data = dataList.get(position);

        // Set the heading text
        holder.headingTextView.setText(data.getHeading());

        // Create and set up the horizontal RecyclerView adapter
        HorizontalAdapter horizontalAdapter = new HorizontalAdapter(data.getCardDataList());
        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        holder.horizontalRecyclerView.setLayoutManager(layoutManager);
        holder.horizontalRecyclerView.setAdapter(horizontalAdapter);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class VerticalViewHolder extends RecyclerView.ViewHolder {
        RecyclerView horizontalRecyclerView;
        TextView headingTextView;

        public VerticalViewHolder(@NonNull View itemView) {
            super(itemView);
            horizontalRecyclerView = itemView.findViewById(R.id.horizontalRecyclerView);
            headingTextView = itemView.findViewById(R.id.headingTextView);
        }
    }
}

