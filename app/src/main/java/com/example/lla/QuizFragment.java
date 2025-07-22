package com.example.lla;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class QuizFragment extends Fragment {

    private String selecetedTopicName="";

    public QuizFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);
        final LinearLayout java = view.findViewById(R.id.javaLayout);
        final LinearLayout php = view.findViewById(R.id.phpLayout);
        final LinearLayout html =view.findViewById(R.id.htmlLayout);
        final LinearLayout android =view.findViewById(R.id.androidLayout);
        final Button startBtn=view.findViewById(R.id.startQuizbtn);
        java.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selecetedTopicName= "java";

                java.setBackgroundResource(R.drawable.round_back_white_stroke);

                php.setBackgroundResource(R.drawable.round_back_white10);
                html.setBackgroundResource(R.drawable.round_back_white10);
                android.setBackgroundResource(R.drawable.round_back_white10);
            }
        });
        php.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecetedTopicName= "php";

                php.setBackgroundResource(R.drawable.round_back_white_stroke);

                java.setBackgroundResource(R.drawable.round_back_white10);
                html.setBackgroundResource(R.drawable.round_back_white10);
                android.setBackgroundResource(R.drawable.round_back_white10);
            }
        });
        html.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecetedTopicName= "html";

                html.setBackgroundResource(R.drawable.round_back_white_stroke);

                java.setBackgroundResource(R.drawable.round_back_white10);
                php.setBackgroundResource(R.drawable.round_back_white10);
                android.setBackgroundResource(R.drawable.round_back_white10);
            }
        });
        android.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecetedTopicName= "android";

                android.setBackgroundResource(R.drawable.round_back_white_stroke);

                java.setBackgroundResource(R.drawable.round_back_white10);
                html.setBackgroundResource(R.drawable.round_back_white10);
                php.setBackgroundResource(R.drawable.round_back_white10);
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selecetedTopicName.isEmpty()){
                    Toast.makeText(getActivity(), "Please select the topic", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent =new Intent(getActivity(),QuizActivity.class);
                    intent.putExtra("selectedTopic",selecetedTopicName);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                }
            }
        });
        return view;
    }
}