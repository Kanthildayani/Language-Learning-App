package com.example.lla;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class QuizActivity extends AppCompatActivity {

    private TextView questions;
    private TextView question;

    private AppCompatButton option1,option2,option3,option4;
    private AppCompatButton nextbtn;
    private Timer quizTimer;

    private int totalTimeinMins=1;
    private int second=0;
    private List<QuestionList> questionLists;
    private int currentQuestionPosition =0;

    private String selectedOptionByUser = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        final ImageView backbtn=findViewById(R.id.backbtn);
        final TextView timer=findViewById(R.id.timer);
        final TextView selectedTopicName=findViewById(R.id.topicname);

        questions=findViewById(R.id.questions);
        question=findViewById(R.id.question);

        final String getselectedTopicName =getIntent().getStringExtra("selectedTopic");

        option1=findViewById(R.id.option1);
        option2=findViewById(R.id.option2);
        option3=findViewById(R.id.option3);
        option4=findViewById(R.id.option4);
        nextbtn=findViewById(R.id.nextbtn);

        selectedTopicName.setText(getselectedTopicName);

        questionLists=QuestionBank.getQuestion(getselectedTopicName);
        startTimer(timer);

        questions.setText((currentQuestionPosition+1)+"/"+questionLists.size());
        question.setText(questionLists.get(0).getQuestion());
        option1.setText(questionLists.get(0).getOption1());
        option2.setText(questionLists.get(0).getOption2());
        option3.setText(questionLists.get(0).getOption3());
        option4.setText(questionLists.get(0).getOption4());

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedOptionByUser .isEmpty()){

                    selectedOptionByUser = option1.getText().toString();

                    option1.setBackgroundResource(R.drawable.round_back_red);
                    option1.setTextColor(Color.WHITE);

                    revealAnswer();

                    questionLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);

                }

            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedOptionByUser .isEmpty()){

                    selectedOptionByUser = option2.getText().toString();

                    option2.setBackgroundResource(R.drawable.round_back_red);
                    option2.setTextColor(Color.WHITE);

                    revealAnswer();

                    questionLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);

                }
            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedOptionByUser .isEmpty()){

                    selectedOptionByUser = option3.getText().toString();

                    option3.setBackgroundResource(R.drawable.round_back_red);
                    option3.setTextColor(Color.WHITE);

                    revealAnswer();

                    questionLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);

                }

            }
        });
        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedOptionByUser .isEmpty()){

                    selectedOptionByUser = option4.getText().toString();

                    option4.setBackgroundResource(R.drawable.round_back_red);
                    option4.setTextColor(Color.WHITE);

                    revealAnswer();

                    questionLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);

                }
            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedOptionByUser.isEmpty()){
                    Toast.makeText(QuizActivity.this, "Please select an option", Toast.LENGTH_SHORT).show();
                }
                else {
                    changeNextQuestion();
                }
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quizTimer.purge();
                quizTimer.cancel();

                startActivity(new Intent(QuizActivity.this,MainActivity.class));
                finish();
            }
        });
    }

    private void startTimer(TextView timerTextview){
        quizTimer=new Timer();
        quizTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(second == 0){
                    totalTimeinMins--;
                    second = 59;
                } else if (second==0 && totalTimeinMins==0) {
                    quizTimer.purge();
                    quizTimer.cancel();

                    Toast.makeText(QuizActivity.this, "Time Over", Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(QuizActivity.this,QuizResults.class);
                    intent.putExtra("Correct",getCorrectAnswer() );
                    intent.putExtra("InCorrect",getInCorrectAnswer());
                    startActivity(intent);
                    finish();
                }
                else {
                    second--;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (second==0 && totalTimeinMins==0) {
                            quizTimer.purge();
                            quizTimer.cancel();

                            Toast.makeText(QuizActivity.this, "Time Over", Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent(QuizActivity.this,QuizResults.class);
                            intent.putExtra("Correct",getCorrectAnswer() );
                            intent.putExtra("InCorrect",getInCorrectAnswer());
                            startActivity(intent);

                            finish();
                        }

                        String finalMinutes = String.valueOf(totalTimeinMins);
                        String finalSeconds= String.valueOf(second);

                        if(finalMinutes.length() == 1){
                            finalMinutes= "0"+finalMinutes;

                        }
                        if(finalSeconds.length() == 1){
                            finalSeconds = "0"+finalSeconds;
                        }

                        timerTextview.setText(finalMinutes +":" +finalSeconds);

                    }
                });
            }
        },1000,1000);

    }

    private int getCorrectAnswer(){

        int correctAnswers = 0;

        for(int i=0;i<questionLists.size();i++){
            final String getUserSelectedAnswer=questionLists.get(i).getUserSelectedAnswer();
            final String getAnswer= questionLists.get(i).getAnswer();

            if(getUserSelectedAnswer.equals(getAnswer)){
                correctAnswers++;


            }
        }
        return  correctAnswers;

    }
    private int getInCorrectAnswer(){

        int incorrectAnswers = 0;

        for(int i=0;i<questionLists.size();i++){
            final String getUserSelectedAnswer=questionLists.get(i).getUserSelectedAnswer();
            final String getAnswer= questionLists.get(i).getAnswer();

            if(!getUserSelectedAnswer.equals(getAnswer)){
                incorrectAnswers++;


            }
        }
        return  incorrectAnswers;

    }

    private void changeNextQuestion(){
        currentQuestionPosition++;

        if((currentQuestionPosition+1)==questionLists.size()){
            nextbtn.setText("Submit Quiz ");
        }
        if(currentQuestionPosition<questionLists.size()){
            selectedOptionByUser="";
            option1.setBackgroundResource(R.drawable.round_back_white_stroke2);
            option1.setTextColor(Color.parseColor("#1F6BB8"));

            option2.setBackgroundResource(R.drawable.round_back_white_stroke2);
            option2.setTextColor(Color.parseColor("#1F6BB8"));

            option3.setBackgroundResource(R.drawable.round_back_white_stroke2);
            option3.setTextColor(Color.parseColor("#1F6BB8"));

            option4.setBackgroundResource(R.drawable.round_back_white_stroke2);
            option4.setTextColor(Color.parseColor("#1F6BB8"));

            questions.setText((currentQuestionPosition+1)+"/"+questionLists.size());
            question.setText(questionLists.get(currentQuestionPosition).getQuestion());
            option1.setText(questionLists.get(currentQuestionPosition).getOption1());
            option2.setText(questionLists.get(currentQuestionPosition).getOption2());
            option3.setText(questionLists.get(currentQuestionPosition).getOption3());
            option4.setText(questionLists.get(currentQuestionPosition).getOption4());

        }
        else {
            Intent intent=new Intent(QuizActivity.this,QuizResults.class);
            intent.putExtra("Correct",getCorrectAnswer());
            intent.putExtra("InCorrect",getInCorrectAnswer());
            startActivity(intent);

            finish();
        }
    }

    public void onBackPressed() {
        quizTimer.purge();
        quizTimer.cancel();

        startActivity(new Intent(QuizActivity.this,MainActivity.class));
        finish();
    }

    private void revealAnswer(){
        final String getAnswer=questionLists.get(currentQuestionPosition).getAnswer();

        if(option1.getText().toString().equals(getAnswer)){
            option1.setBackgroundResource(R.drawable.round_back_green10 );
            option1.setTextColor(Color.WHITE);

        }
        else if (option2.getText().toString().equals(getAnswer)){
            option2.setBackgroundResource(R.drawable.round_back_green10 );
            option2.setTextColor(Color.WHITE);
        }
        else if (option3.getText().toString().equals(getAnswer)){
            option3.setBackgroundResource(R.drawable.round_back_green10 );
            option3.setTextColor(Color.WHITE);
        }
        else if (option4.getText().toString().equals(getAnswer)){
            option4.setBackgroundResource(R.drawable.round_back_green10 );
            option4.setTextColor(Color.WHITE);
        }
    }

            }


