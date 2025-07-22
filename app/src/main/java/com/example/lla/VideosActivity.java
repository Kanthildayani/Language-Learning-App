package com.example.lla;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.ListView;
import android.widget.MediaController;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import java.util.ArrayList;
import java.util.List;

public class VideosActivity extends AppCompatActivity {

    VideoView videoView;
    ListView listView;

    Button completedvideo;
    ArrayList<String> videolist;
    private int selectedItem = -1;
    ArrayAdapter adapter;

    ProgressBar videoProgressBar;

    ImageView imageView2;

    ImageButton backward;


    TextView textViewHeading,points,total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
        points = findViewById(R.id.point);
        videoView = findViewById(R.id.videoview);
        listView = findViewById(R.id.listview);
        textViewHeading = findViewById(R.id.textViewHeading);
        videoProgressBar = findViewById(R.id.videoProgressBar);
        imageView2 = findViewById(R.id.imageView2);
        total = findViewById(R.id.total);
        completedvideo = findViewById(R.id.completed);
        backward = findViewById(R.id.backward);


        String heading = getIntent().getStringExtra("text1");
        int image = getIntent().getIntExtra("imageResource1", 0);

        textViewHeading.setText(heading);
        imageView2.setImageResource(image);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.list_item_animation);
        LayoutAnimationController controller = new LayoutAnimationController(animation);
        controller.setDelay(0.8f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        listView.setLayoutAnimation(controller);
        // Set the heading to the TextView
        TextView textViewHeading = findViewById(R.id.textViewHeading);
        textViewHeading.setText(heading);

        ImageView imageView = findViewById(R.id.imageView2);
        imageView.setImageResource(image);

        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(VideosActivity.this,MainActivity.class);
                startActivity(a);
            }
        });

        if(heading.equals("Python"))
        {
            videolist = new ArrayList<>();
            videolist.add("1. Introduction");
            videolist.add("2. Operators");
            videolist.add("3. Identifiers");
            videolist.add("4. Syntax");
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, videolist) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView textView = view.findViewById(android.R.id.text1);

                    // Apply the appropriate background color based on the selected item
                    if (position == selectedItem) {
                        textView.setBackgroundColor(Color.GREEN);
                    } else {
                        textView.setBackgroundColor(Color.RED);
                    }

                    return view;
                }
            };

            listView.setAdapter(adapter);
            int itemCount = adapter.getCount();
            total.setText(" / " + String.valueOf(itemCount));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectedItem = position;

                    // Notify the adapter that the data has changed
                    adapter.notifyDataSetChanged();
                    switch (position){
                        case 0:
                            videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.intro_p));
                            int currentPosition = videoView.getCurrentPosition();
                            SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putInt("videoPosition", currentPosition);
                            editor.apply();
                            int savedPosition = preferences.getInt("videoPosition", 0);
// Set the saved position to the VideoView
                            videoView.seekTo(savedPosition);
                            videoProgressBar.setProgress(100);
                            break;
                        case 1:
                            videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.operators_p));
                            break;
                        case 2:
                            videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.identifiers_p));
                            break;
                        case 3:
                            videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.syntax_p));
                            break;
                        default:
                            break;


                    }
                    videoView.setMediaController(new MediaController(VideosActivity.this));
                    videoView.requestFocus();
                    videoView.start();
                    videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            // Get the duration of the video
                            int duration = videoView.getDuration();

                            // Calculate the halfway point
                            int halfwayPoint = duration / 2;

                            // Get the current position of the video playback
                            int currentPosition = videoView.getCurrentPosition();

                            // Check if the video playback reached the halfway point
                            if (currentPosition >= halfwayPoint) {
                                // Display a toast message
                                // Increase the point in the TextView by 1
                                int currentPoints = Integer.parseInt(points.getText().toString());
                                int updatedPoints = currentPoints + 1;
                                points.setText(String.valueOf(updatedPoints));

                                if(itemCount == updatedPoints)
                                {
                                    final Context context = VideosActivity.this;
                                    completedvideo.setVisibility(View.VISIBLE);

                                    final DatabaseHelper dbHelper = new DatabaseHelper(context);
                                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                                    ContentValues values = new ContentValues();
                                    values.put(DatabaseHelper.COLUMN_HEADING, heading);
                                    values.put(DatabaseHelper.COLUMN_IMAGE, image);
                                    long newRowId = db.insert(DatabaseHelper.TABLE_NAME, null, values);
                                    db.close();
                                    completedvideo.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent a = new Intent(VideosActivity.this,MainActivity.class);
                                            startActivity(a);
                                        }
                                    });
                                }
                            }
                            Toast.makeText(VideosActivity.this, "Video completed", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });

        }
        if(heading.equals("Spanish")) {
            videolist = new ArrayList<>();
            videolist.add("1. Introduction");
            videolist.add("2. Alphabets");
            videolist.add("3. Numbers");
            videolist.add("4.Week");
            videolist.add("5.Conversation");

            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, videolist);
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, videolist) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView textView = view.findViewById(android.R.id.text1);

                    // Apply the appropriate background color based on the selected item
                    if (position == selectedItem) {
                        textView.setBackgroundColor(Color.GREEN);
                    } else {
                        textView.setBackgroundColor(Color.RED);
                    }

                    return view;
                }
            };
            listView.setAdapter(adapter);
            int itemCount = adapter.getCount();
            total.setText(" / " + String.valueOf(itemCount));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectedItem = position;

                    // Notify the adapter that the data has changed
                    adapter.notifyDataSetChanged();
                    switch (position) {
                        case 0:
                            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.introduction_s));
                            break;
                        case 1:
                            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.alphabets_s));
                            break;
                        case 2:
                            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.numbers));
                            break;
                        case 3:
                            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.weeks));
                            break;
                        case 4:
                            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.conversation));
                            break;
                        default:
                            break;
                    }
                    videoView.setMediaController(new MediaController(VideosActivity.this));
                    videoView.requestFocus();
                    videoView.start();
                    videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            // Get the duration of the video
                            int duration = videoView.getDuration();

                            // Calculate the halfway point
                            int halfwayPoint = duration / 2;

                            // Get the current position of the video playback
                            int currentPosition = videoView.getCurrentPosition();

                            // Check if the video playback reached the halfway point
                            if (currentPosition >= halfwayPoint) {
                                // Display a toast message
                                // Increase the point in the TextView by 1
                                int currentPoints = Integer.parseInt(points.getText().toString());
                                int updatedPoints = currentPoints + 1;
                                points.setText(String.valueOf(updatedPoints));
                                if(itemCount == updatedPoints)
                                {
                                    final Context context = VideosActivity.this;
                                    completedvideo.setVisibility(View.VISIBLE);

                                    final DatabaseHelper dbHelper = new DatabaseHelper(context);
                                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                                    ContentValues values = new ContentValues();
                                    values.put(DatabaseHelper.COLUMN_HEADING, heading);
                                    values.put(DatabaseHelper.COLUMN_IMAGE, image);
                                    long newRowId = db.insert(DatabaseHelper.TABLE_NAME, null, values);
                                    db.close();
                                    completedvideo.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent a = new Intent(VideosActivity.this,MainActivity.class);
                                            startActivity(a);
                                        }
                                    });
                                }
                            }
                            Toast.makeText(VideosActivity.this, "Video completed", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        }
        if(heading.equals("Germany")) {
            videolist = new ArrayList<>();
            videolist.add("1. Introduction");
            videolist.add("2. Alphabets");
            videolist.add("3. Basics");

            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, videolist);
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, videolist) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView textView = view.findViewById(android.R.id.text1);

                    // Apply the appropriate background color based on the selected item
                    if (position == selectedItem) {
                        textView.setBackgroundColor(Color.GREEN);
                    } else {
                        textView.setBackgroundColor(Color.RED);
                    }

                    return view;
                }
            };
            listView.setAdapter(adapter);
            int itemCount = adapter.getCount();
            total.setText(" / " + String.valueOf(itemCount));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectedItem = position;

                    // Notify the adapter that the data has changed
                    adapter.notifyDataSetChanged();
                    switch (position) {
                        case 0:
                            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.introduction_g));
                            break;
                        case 1:
                            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.alphabets));
                            break;
                        case 2:
                            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.basic_information));
                            break;
                        default:
                            break;
                    }
                    videoView.setMediaController(new MediaController(VideosActivity.this));
                    videoView.requestFocus();
                    videoView.start();
                    videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            // Get the duration of the video
                            int duration = videoView.getDuration();

                            // Calculate the halfway point
                            int halfwayPoint = duration / 2;

                            // Get the current position of the video playback
                            int currentPosition = videoView.getCurrentPosition();

                            // Check if the video playback reached the halfway point
                            if (currentPosition >= halfwayPoint) {
                                // Display a toast message
                                // Increase the point in the TextView by 1
                                int currentPoints = Integer.parseInt(points.getText().toString());
                                int updatedPoints = currentPoints + 1;
                                points.setText(String.valueOf(updatedPoints));
                                if(itemCount == updatedPoints)
                                {
                                    final Context context = VideosActivity.this;
                                    completedvideo.setVisibility(View.VISIBLE);

                                    final DatabaseHelper dbHelper = new DatabaseHelper(context);
                                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                                    ContentValues values = new ContentValues();
                                    values.put(DatabaseHelper.COLUMN_HEADING, heading);
                                    values.put(DatabaseHelper.COLUMN_IMAGE, image);
                                    long newRowId = db.insert(DatabaseHelper.TABLE_NAME, null, values);
                                    db.close();
                                    completedvideo.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent a = new Intent(VideosActivity.this,MainActivity.class);
                                            startActivity(a);
                                        }
                                    });
                                }
                            }
                            Toast.makeText(VideosActivity.this, "Video completed", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        }
        if(heading.equals("Reading")) {
            videolist = new ArrayList<>();
            videolist.add("1. Introduction");
            videolist.add("2. Intensive");
            videolist.add("3. Comprehension");
            videolist.add("4.Scanning");
            videolist.add("5.Scimming");

            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, videolist);
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, videolist) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView textView = view.findViewById(android.R.id.text1);

                    // Apply the appropriate background color based on the selected item
                    if (position == selectedItem) {
                        textView.setBackgroundColor(Color.GREEN);
                    } else {
                        textView.setBackgroundColor(Color.RED);
                    }

                    return view;
                }
            };
            listView.setAdapter(adapter);
            int itemCount = adapter.getCount();
            total.setText(" / " + String.valueOf(itemCount));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectedItem = position;

                    // Notify the adapter that the data has changed
                    adapter.notifyDataSetChanged();
                    switch (position) {
                        case 0:
                            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.introduction_r));
                            break;
                        case 1:
                            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intensive_extensive));
                            break;
                        case 2:
                            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.comprehension));
                            break;
                        case 3:
                            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.scanning));
                            break;
                        case 4:
                            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.scimming));
                            break;
                        default:
                            break;
                    }
                    videoView.setMediaController(new MediaController(VideosActivity.this));
                    videoView.requestFocus();
                    videoView.start();
                    videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            // Get the duration of the video
                            int duration = videoView.getDuration();

                            // Calculate the halfway point
                            int halfwayPoint = duration / 2;

                            // Get the current position of the video playback
                            int currentPosition = videoView.getCurrentPosition();

                            // Check if the video playback reached the halfway point
                            if (currentPosition >= halfwayPoint) {
                                // Display a toast message
                                // Increase the point in the TextView by 1
                                int currentPoints = Integer.parseInt(points.getText().toString());
                                int updatedPoints = currentPoints + 1;
                                points.setText(String.valueOf(updatedPoints));
                                if(itemCount == updatedPoints)
                                {
                                    final Context context = VideosActivity.this;
                                    completedvideo.setVisibility(View.VISIBLE);

                                    final DatabaseHelper dbHelper = new DatabaseHelper(context);
                                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                                    ContentValues values = new ContentValues();
                                    values.put(DatabaseHelper.COLUMN_HEADING, heading);
                                    values.put(DatabaseHelper.COLUMN_IMAGE, image);
                                    long newRowId = db.insert(DatabaseHelper.TABLE_NAME, null, values);
                                    db.close();
                                    completedvideo.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent a = new Intent(VideosActivity.this,MainActivity.class);
                                            startActivity(a);
                                        }
                                    });
                                }
                            }
                            Toast.makeText(VideosActivity.this, "Video completed", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        }
        if(heading.equals("Listening")) {
            videolist = new ArrayList<>();
            videolist.add("1. Introduction");
            videolist.add("2. Active Listening");
            videolist.add("3. Types");

            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, videolist);
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, videolist) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView textView = view.findViewById(android.R.id.text1);

                    // Apply the appropriate background color based on the selected item
                    if (position == selectedItem) {
                        textView.setBackgroundColor(Color.GREEN);
                    } else {
                        textView.setBackgroundColor(Color.RED);
                    }

                    return view;
                }
            };
            listView.setAdapter(adapter);
            int itemCount = adapter.getCount();
            total.setText(" / " + String.valueOf(itemCount));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectedItem = position;

                    // Notify the adapter that the data has changed
                    adapter.notifyDataSetChanged();
                    switch (position) {
                        case 0:
                            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.introductionl));
                            break;
                        case 1:
                            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.active));
                            break;
                        case 2:
                            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.types));
                            break;
                        default:
                            break;
                    }
                    videoView.setMediaController(new MediaController(VideosActivity.this));
                    videoView.requestFocus();
                    videoView.start();
                    videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            // Get the duration of the video
                            int duration = videoView.getDuration();

                            // Calculate the halfway point
                            int halfwayPoint = duration / 2;

                            // Get the current position of the video playback
                            int currentPosition = videoView.getCurrentPosition();

                            // Check if the video playback reached the halfway point
                            if (currentPosition >= halfwayPoint) {
                                // Display a toast message
                                // Increase the point in the TextView by 1
                                int currentPoints = Integer.parseInt(points.getText().toString());
                                int updatedPoints = currentPoints + 1;
                                points.setText(String.valueOf(updatedPoints));
                                if(itemCount == updatedPoints)
                                {
                                    final Context context = VideosActivity.this;
                                    completedvideo.setVisibility(View.VISIBLE);

                                    final DatabaseHelper dbHelper = new DatabaseHelper(context);
                                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                                    ContentValues values = new ContentValues();
                                    values.put(DatabaseHelper.COLUMN_HEADING, heading);
                                    values.put(DatabaseHelper.COLUMN_IMAGE, image);
                                    long newRowId = db.insert(DatabaseHelper.TABLE_NAME, null, values);
                                    db.close();
                                    completedvideo.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent a = new Intent(VideosActivity.this,MainActivity.class);
                                            startActivity(a);
                                        }
                                    });
                                }
                            }
                            Toast.makeText(VideosActivity.this, "Video completed", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        }

        if(heading.equals("Java")) {
            videolist = new ArrayList<>();
            videolist.add("1. Introduction");
            videolist.add("2. Features");
            videolist.add("3. Constructors");
            videolist.add("4. Class and Objects");
            videolist.add("5. Inheritance");
            videolist.add("6. Abstraction");
            videolist.add("7. Syntax");
            videolist.add("8. API");
            videolist.add("9.Coupling");

            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, videolist);
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, videolist) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView textView = view.findViewById(android.R.id.text1);

                    // Apply the appropriate background color based on the selected item
                    if (position == selectedItem) {
                        textView.setBackgroundColor(Color.GREEN);
                    } else {
                        textView.setBackgroundColor(Color.RED);
                    }

                    return view;
                }
            };
            listView.setAdapter(adapter);
            int itemCount = adapter.getCount();
            total.setText(" / " + String.valueOf(itemCount));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectedItem = position;

                    // Notify the adapter that the data has changed
                    adapter.notifyDataSetChanged();
                    switch (position) {
                        case 0:
                            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro));
                            break;
                        case 1:
                            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.features));
                            break;
                        case 2:
                            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.constructor));
                            break;
                        case 3:
                            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.classes));
                            break;
                        case 4:
                            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.inheritance));
                            break;
                        case 5:
                            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.abstraction));
                            break;
                        case 6:
                            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.syntax));
                            break;
                        case 7:
                            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.api));
                            break;
                        case 8:
                            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.coupling));
                            break;
                        default:
                            break;
                    }
                    videoView.setMediaController(new MediaController(VideosActivity.this));
                    videoView.requestFocus();
                    videoView.start();
                    videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            // Get the duration of the video
                            int duration = videoView.getDuration();

                            // Calculate the halfway point
                            int halfwayPoint = duration / 2;

                            // Get the current position of the video playback
                            int currentPosition = videoView.getCurrentPosition();

                            // Check if the video playback reached the halfway point
                            if (currentPosition >= halfwayPoint) {
                                // Display a toast message
                                // Increase the point in the TextView by 1
                                int currentPoints = Integer.parseInt(points.getText().toString());
                                int updatedPoints = currentPoints + 1;
                                points.setText(String.valueOf(updatedPoints));
                                if(itemCount == updatedPoints)
                                {
                                    final Context context = VideosActivity.this;
                                    completedvideo.setVisibility(View.VISIBLE);

                                    final DatabaseHelper dbHelper = new DatabaseHelper(context);
                                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                                    ContentValues values = new ContentValues();
                                    values.put(DatabaseHelper.COLUMN_HEADING, heading);
                                    values.put(DatabaseHelper.COLUMN_IMAGE, image);
                                    long newRowId = db.insert(DatabaseHelper.TABLE_NAME, null, values);
                                    db.close();
                                    completedvideo.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent a = new Intent(VideosActivity.this,MainActivity.class);
                                            startActivity(a);
                                        }
                                    });
                                }
                            }
                            Toast.makeText(VideosActivity.this, "Video is completed", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        }
    }
}