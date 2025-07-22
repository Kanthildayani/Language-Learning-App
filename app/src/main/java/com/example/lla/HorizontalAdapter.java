package com.example.lla;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.HorizontalViewHolder> {
    private List<CardDataModel> dataList;

    public HorizontalAdapter(List<CardDataModel> dataList) {
        this.dataList = dataList;

    }

    @NonNull
    @Override
    public HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item_layout, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalViewHolder holder, int position) {
        CardDataModel data = dataList.get(position);
        // Bind data to the CardView elements
        ImageView imageView = holder.itemView.findViewById(R.id.imageView);
        TextView textView = holder.itemView.findViewById(R.id.textView);
        holder.itemView.setBackgroundColor(data.getColor());
        imageView.setImageResource(data.getImageResource());
        textView.setText(data.getText());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the text from the clicked card
                String text;
                int image;
                text = textView.getText().toString();
                image = data.getImageResource();

                // Create an intent to start the detail activity
                Context context = v.getContext();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("text", text);
                intent.putExtra("imageResource", image);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class HorizontalViewHolder extends RecyclerView.ViewHolder {
        public HorizontalViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

