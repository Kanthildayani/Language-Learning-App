package com.example.lla;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
BottomNavigationView btm;
FragmentContainerView fm;


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btm = findViewById(R.id.bottom_navigation);
        fm = findViewById(R.id.fragmentContainer);


        btm.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fm = null;
                if (item.getItemId() == R.id.nav_home) {
                    fm = new HomeFragment();
                } else if (item.getItemId() == R.id.nav_quiz) {
                    fm = new QuizFragment();
                } else if (item.getItemId() == R.id.nav_past) {
                    fm = new PastFragment();
                } else if (item.getItemId() == R.id.nav_Account) {
                    fm = new AccountFragment();
                }
                if (fm != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainer, fm)
                            .commit();
                }

                return true;
            }
        });



    }
}