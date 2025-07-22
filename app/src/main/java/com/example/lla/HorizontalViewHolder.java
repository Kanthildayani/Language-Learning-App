package com.example.lla;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class HorizontalViewHolder extends RecyclerView.ViewHolder {
    RecyclerView recyclerView;
    TextView headingTextView;

    public HorizontalViewHolder(View itemView) {
        super(itemView);
        recyclerView = itemView.findViewById(R.id.horizontalRecyclerView);
        headingTextView = itemView.findViewById(R.id.headingTextView);
    }
}
