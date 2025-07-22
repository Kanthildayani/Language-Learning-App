package com.example.lla;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VideoAdapter  extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private Context context;
    private List<VideoItem> videoItemList;
    private int[] cardColors; // Array of colors
    public VideoAdapter(Context context, List<VideoItem> videoItemList) {
        this.context = context;
        this.videoItemList = videoItemList;
        this.cardColors = context.getResources().getIntArray(R.array.card_colors);
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_completed_video, parent, false);
        return new VideoViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        VideoItem videoItem = videoItemList.get(position);

        // Bind data to the views
        holder.textViewHeading.setText(videoItem.getHeading());
        holder.imageView.setImageResource(videoItem.getImage());
        int color = cardColors[position % cardColors.length]; // Use modulus to cycle through colors
        holder.itemView.setBackgroundColor(color);// Set the image resource
    }

    @Override
    public int getItemCount() {
        return videoItemList.size();
    }
    public class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewHeading;

        public VideoViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewHeading = itemView.findViewById(R.id.textViewHeading);
        }
    }
}
