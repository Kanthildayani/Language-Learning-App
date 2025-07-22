package com.example.lla;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView verticalRecyclerView;
    private VerticalAdapter verticalAdapter;

    private TextView names;

   private ImageView circle1;
    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        circle1 = rootView.findViewById(R.id.circularImageView2);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(userId);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String image = dataSnapshot.child("profileImageUrl").getValue(String.class);

                    names = rootView.findViewById(R.id.name);
                    circle1 = rootView.findViewById(R.id.circularImageView2);

                    names.setText(name);
                    if(image !=null && !image.isEmpty())
                    {
                        Picasso.get()
                                .load(image)
                                .placeholder(R.drawable.download)
                                .error(R.drawable.progress_drawable)
                                .into(circle1);
                    }
                } else {
                    // Handle the case where the data doesn't exist or the user ID is invalid.
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors that may occur.
            }
        });
        verticalRecyclerView = rootView.findViewById(R.id.verticalRecyclerView);
        verticalRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Create your data models for the vertical RecyclerView
        List<HorizontalDataModel> dataList = createVerticalDataList();

        verticalAdapter = new VerticalAdapter(dataList);
        verticalRecyclerView.setAdapter(verticalAdapter);


        return rootView;
    }


    private List<HorizontalDataModel> createVerticalDataList() {
        // Create a list of HorizontalDataModel objects
        // Each HorizontalDataModel object represents a horizontal RecyclerView item with a heading and a list of CardDataModel objects
        // Populate the data as per your requirements
        List<HorizontalDataModel> dataList = new ArrayList<>();
        // Create the first horizontal RecyclerView item
        List<CardDataModel> cardDataList1 = new ArrayList<>();
        cardDataList1.add(new CardDataModel(R.drawable.python, "Python", getActivity().getColor(R.color.shrine_pink_100)));
        cardDataList1.add(new CardDataModel(R.drawable.java, "Java",getActivity().getColor(R.color.light_yellow)));
        cardDataList1.add(new CardDataModel(R.drawable.c, "C",getActivity().getColor(R.color.light_blue)));
        cardDataList1.add(new CardDataModel(R.drawable.c66, "C++",getActivity().getColor(R.color.dark_blue)));
        cardDataList1.add(new CardDataModel(R.drawable.ruby, "Ruby",getActivity().getColor(R.color.Red)));
        cardDataList1.add(new CardDataModel(R.drawable.jaavascript, "Javascript",getActivity().getColor(R.color.purple)));
        cardDataList1.add(new CardDataModel(R.drawable.php, "PHP",getActivity().getColor(R.color.blue)));
        cardDataList1.add(new CardDataModel(R.drawable.html, "html",getActivity().getColor(R.color.brown)));

        HorizontalDataModel horizontalDataModel1 = new HorizontalDataModel("Computer Languages >", cardDataList1);
        dataList.add(horizontalDataModel1);

        //  the second horizontal RecyclerView item
        List<CardDataModel> cardDataList2 = new ArrayList<>();
        cardDataList2.add(new CardDataModel(R.drawable.hearing, "Listening",getActivity().getColor(R.color.shrine_pink_100)));
        cardDataList2.add(new CardDataModel(R.drawable.reading, "Reading",getActivity().getColor(R.color.light_yellow)));
        cardDataList2.add(new CardDataModel(R.drawable.speaking, "Speaking",getActivity().getColor(R.color.light_blue)));
        cardDataList2.add(new CardDataModel(R.drawable.comprehension, "Comprhension",getActivity().getColor(R.color.dark_blue)));
        cardDataList2.add(new CardDataModel(R.drawable.vocablury, "Vocablury",getActivity().getColor(R.color.Red)));


        HorizontalDataModel horizontalDataModel2 = new HorizontalDataModel("English skills >", cardDataList2);
        dataList.add(horizontalDataModel2);

        //  the third horizontal RecyclerView item
        List<CardDataModel> cardDataList3 = new ArrayList<>();
        cardDataList3.add(new CardDataModel(R.drawable.germany, "German",getActivity().getColor(R.color.shrine_pink_100)));
        cardDataList3.add(new CardDataModel(R.drawable.spain, "Spanish",getActivity().getColor(R.color.light_yellow)));
        cardDataList3.add(new CardDataModel(R.drawable.eng, "English",getActivity().getColor(R.color.light_blue)));
        cardDataList3.add(new CardDataModel(R.drawable.hindi, "Hindi",getActivity().getColor(R.color.dark_blue)));
        cardDataList3.add(new CardDataModel(R.drawable.french, "French",getActivity().getColor(R.color.Red)));
//        cardDataList3.add(new CardDataModel(R.drawable.bihari, "Bihari",getActivity().getColor(R.color.purple)));
//        cardDataList3.add(new CardDataModel(R.drawable.sindhi, "Sindhi",getActivity().getColor(R.color.blue)));
//        cardDataList3.add(new CardDataModel(R.drawable.malyalam, "Malyalam",getActivity().getColor(R.color.brown)));


        HorizontalDataModel horizontalDataModel3 = new HorizontalDataModel("Human Languages >", cardDataList3);
        dataList.add(horizontalDataModel3);

        return dataList;
    }
}