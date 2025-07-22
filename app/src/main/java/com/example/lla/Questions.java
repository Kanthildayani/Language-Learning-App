package com.example.lla;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Questions extends AppCompatActivity {
    private LinearLayout faqList;
    private View selectedItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        faqList = findViewById(R.id.faq_list);

        addFAQItem("What is the name of our language learning Quiz application?", "Language Learning Application");
        addFAQItem("How does our Quiz application help users learn new languages?", "By providing instructional videos in various languages to enhance language comprehension and communication skills.");
        addFAQItem("In which section of the application can you find videos for language learning?", "Language Learning Videos section.");
        addFAQItem("What is the primary focus of the language learning videos in our app?", "The primary focus is to teach conversational language skills and practical usage.");
        addFAQItem("How can users test their understanding of a new language after watching the videos?", " By attempting interactive quizzes and exercises related to the video content.");
        addFAQItem("Which feature allows users to track their progress in the language learning journey?", "Progress Tracker");
        addFAQItem("Are the language learning videos conducted by native speakers of the respective languages?", " Yes, the videos are conducted by native speakers to ensure authenticity and accuracy.");
        addFAQItem("What are some of the interactive elements incorporated into the language learning videos?", " Interactive subtitles, quizzes, and pronunciation exercises.");
        addFAQItem("How does our app cater to users with different proficiency levels in a language?", "The app offers videos at various difficulty levels, from beginner to advanced.");
        addFAQItem("Can users access the language learning videos offline for on-the-go learning?", "Not Yet , but soon new feature can be added");
        addFAQItem("What differentiates our Quiz application from other language learning platforms?", "Our app focuses on engaging video content for practical language use, making learning enjoyable.");
        addFAQItem("How can users provide feedback or suggestions for improving the language learning experience in our app?", " Users can provide feedback through the app's feedback or contact us section.");
        addFAQItem("What feature in our Quiz application encourages language learners to practice speaking with native speakers?", "Quiz , scores, regular updates");
        addFAQItem("What is the maximum duration of each language learning video in our Quiz application?", "Each course minimum 3 - 5 hours and course video of maximum 15 - 20 mins");
    }

    private void addFAQItem(String question, String answer) {
        View faqItemView = LayoutInflater.from(this).inflate(R.layout.item_faq, faqList, false);
        TextView questionTextView = faqItemView.findViewById(R.id.question_textview);
        TextView answerTextView = faqItemView.findViewById(R.id.answer_textview);

        questionTextView.setText(question);
        answerTextView.setText(answer);

        questionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedItem != null) {
                    selectedItem.setBackground(null);
                }

                if (selectedItem == faqItemView) {
                    selectedItem = null; // Deselect the item if it was previously selected
                } else {
                    selectedItem = faqItemView; // Set the current item as the selected item
                    Drawable borderDrawable = getResources().getDrawable(R.drawable.faq_item_background);
                    selectedItem.setBackground(borderDrawable); // Apply border to the selected item
                }
                if (answerTextView.getVisibility() == View.VISIBLE) {
                    answerTextView.setVisibility(View.GONE);
                } else {
                    answerTextView.setVisibility(View.VISIBLE);

                }
            }
        });

        faqList.addView(faqItemView);
    }
}