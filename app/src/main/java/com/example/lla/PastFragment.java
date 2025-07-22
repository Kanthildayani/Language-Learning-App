package com.example.lla;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PastFragment extends Fragment {
    private RecyclerView recyclerView;
    private TextView fetch;


    public PastFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_past, container, false);
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<VideoItem> videoItemList = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, null, null, null, null, null, null);

        int headingIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_HEADING);
        int imageIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE);

        if (headingIndex >= 0 && imageIndex >= 0) {
            while (cursor.moveToNext()) {
                String heading = cursor.getString(headingIndex);
                int image = cursor.getInt(imageIndex);

                // Create a VideoItem and add it to your list
                videoItemList.add(new VideoItem(heading, image));
            }
        } else {
            // Handle the case where the columns are not found in the cursor result set
        }

        db.close();
        dbHelper.close();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

// Create and set the adapter with the fetched videoItemList
        VideoAdapter adapter = new VideoAdapter(getContext(), videoItemList);
        recyclerView.setAdapter(adapter);
        return view;
    }

}