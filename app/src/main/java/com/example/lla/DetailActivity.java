package com.example.lla;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;


import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class DetailActivity extends AppCompatActivity {

    TextView textViewHeadings;
    ImageView imageViews;

    ConstraintLayout cns;

    TextView details;

    private MediaPlayer mediaPlayer;

    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        cns = findViewById(R.id.back);
        start = findViewById(R.id.start);
        textViewHeadings = findViewById(R.id.textViewHeadings);
        imageViews = findViewById(R.id.imageView2s);
        details = findViewById(R.id.multi);
        String heading = getIntent().getStringExtra("text");
        int image = getIntent().getIntExtra("imageResource", 0);

        textViewHeadings.setText(heading);
        imageViews.setImageResource(image);

        details.setKeyListener(null);
        details.setFocusable(false);
        if (heading.equals("Python")) {
            cns.setBackgroundColor(getResources().getColor(R.color.purple));
            details.setText("1. Programming fundamentals, including variables, loops, and conditionals. \n\n 2. Improved problem-solving skills. \n\n 3. A foundation for machine learning and artificial intelligence projects");
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mediaPlayer = MediaPlayer.create(DetailActivity.this, R.raw.button);
                    mediaPlayer.start();
                    Intent intent = new Intent(DetailActivity.this, VideosActivity.class);
                    intent.putExtra("text1", heading);
                    intent.putExtra("imageResource1", image);
                    startActivity(intent);
                }
            });
        }
        if (heading.equals("Java")) {
            cns.setBackgroundColor(getResources().getColor(R.color.light_yellow));
            details.setText("1. Enhanced problem-solving and computational thinking skills. \n\n 2. Opportunities for automation and efficiency in various tasks. \n\n 3. Entry into data analysis and manipulation. \n\n 4. A gateway to web development and scripting.");
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mediaPlayer = MediaPlayer.create(DetailActivity.this, R.raw.button);
                    mediaPlayer.start();
                    Intent intent = new Intent(DetailActivity.this, VideosActivity.class);
                    intent.putExtra("text1", heading);
                    intent.putExtra("imageResource1", image);
                    startActivity(intent);
                }
            });
        }
        if (heading.equals("Listening")) {
            cns.setBackgroundColor(getResources().getColor(R.color.light_yellow));
            details.setText("1. Helps to Resolve conflicts  \n\n 2. Better understanding Skills  \n\n 3. Boosts Confidence.");
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mediaPlayer = MediaPlayer.create(DetailActivity.this, R.raw.button);
                    mediaPlayer.start();
                    Intent intent = new Intent(DetailActivity.this, VideosActivity.class);
                    intent.putExtra("text1", heading);
                    intent.putExtra("imageResource1", image);
                    startActivity(intent);
                }
            });
        }
        if (heading.equals("Reading")) {
            cns.setBackgroundColor(getResources().getColor(R.color.light_yellow));
            details.setText("1. Increases general knowledge \n\n 2. Keeps mind positive \n\n 3.  Improves concentration.");
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mediaPlayer = MediaPlayer.create(DetailActivity.this, R.raw.button);
                    mediaPlayer.start();
                    Intent intent = new Intent(DetailActivity.this, VideosActivity.class);
                    intent.putExtra("text1", heading);
                    intent.putExtra("imageResource1", image);
                    startActivity(intent);
                }
            });
        }
        if (heading.equals("Germany")) {
            cns.setBackgroundColor(getResources().getColor(R.color.light_yellow));
            details.setText("1. A language of high end business \n\n 2. Study abroad opportunities\n\n 3. Helps brain development");
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mediaPlayer = MediaPlayer.create(DetailActivity.this, R.raw.button);
                    mediaPlayer.start();
                    Intent intent = new Intent(DetailActivity.this, VideosActivity.class);
                    intent.putExtra("text1", heading);
                    intent.putExtra("imageResource1", image);
                    startActivity(intent);
                }
            });
        }
        if (heading.equals("Spanish")) {
            cns.setBackgroundColor(getResources().getColor(R.color.light_yellow));
            details.setText("1. Personal growth \n\n 2. Engaging in other cultures. \n\n 3. Enhance your grammar expertise");
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mediaPlayer = MediaPlayer.create(DetailActivity.this, R.raw.button);
                    mediaPlayer.start();
                    Intent intent = new Intent(DetailActivity.this, VideosActivity.class);
                    intent.putExtra("text1", heading);
                    intent.putExtra("imageResource1", image);
                    startActivity(intent);
                }
            });
        }


    }


}


