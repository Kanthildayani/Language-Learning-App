package com.example.lla;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiInfo;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class AccountFragment extends Fragment {

    private TextView pname,pemail;
    private ImageView circle;
    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        TextView profile,logout,faq,about,settings;

        circle = view.findViewById(R.id.circularImageView);
        pname = view.findViewById(R.id.Pname);
        pemail = view.findViewById(R.id.pemail);
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
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String image = dataSnapshot.child("profileImageUrl").getValue(String.class);

                    pname = view.findViewById(R.id.Pname);
                    pemail = view.findViewById(R.id.pemail);
                    circle = view.findViewById(R.id.circularImageView);

                    pname.setText(name);
                    pemail.setText(email);
                    if(image !=null && !image.isEmpty())
                    {
                        Picasso.get()
                                .load(image)
                                .placeholder(R.drawable.download)
                                .error(R.drawable.progress_drawable)
                                .into(circle);
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

        profile = view.findViewById(R.id.profile);
        faq = view.findViewById(R.id.faq);
        logout = view.findViewById(R.id.logout);
        about = view.findViewById(R.id.about);
        settings = view.findViewById(R.id.settings);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfileActivity();
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomDialog();
            }
        });

        Dialog dialog = new Dialog(getActivity());
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView(R.layout.about_dialog);

                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                Button buttonOk = dialog.findViewById(R.id.btnOK);
                buttonOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        faq.setOnClickListener(new View.OnClickListener() {
            private long lastClickTime = 0;
            @Override
            public void onClick(View v) {
                long clickTime = System.currentTimeMillis();
                if (clickTime - lastClickTime < 500) { // Adjust the double-click interval as needed
                    // Double-click detected
                    openNewActivity();

                }
                lastClickTime = clickTime;

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customExitDialog(R.anim.slide_out_top);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
    private void openNewActivity() {
        Intent intent = new Intent(getActivity(), Questions.class);
        startActivity(intent);
    }
    private void openProfileActivity() {
        Intent intent = new Intent(getActivity(), Profile_info.class);
        startActivity(intent);
    }

    private void customExitDialog(int dialogStyle) {
        // creating custom dialog
        final Dialog dialog = new Dialog(requireContext(),R.style.RoundedCornerDialog);

        // setting content view to dialog
        dialog.setContentView(R.layout.layout_exit);
        dialog.getWindow().getAttributes().windowAnimations = dialogStyle;


        // getting reference of TextView
        Button dialogButtonYes = dialog.findViewById(R.id.textViewYes);
        Button dialogButtonNo = dialog.findViewById(R.id.textViewNo);
        dialogButtonNo.setTextColor(Color.RED);
        dialogButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // dismiss the dialog and exit the exit
                dialog.dismiss();
                requireActivity().finish();

            }
        });
        // click listener for No
        dialogButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // dismiss the dialog
                dialog.dismiss();

            }
        });
        dialog.show();

    }
    private void showBottomDialog(){
        WifiManager wifiManager = (WifiManager) requireContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        boolean isWifiEnabled = wifiManager.isWifiEnabled();
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_layout);

        Switch wifi = dialog.findViewById(R.id.wifi);
        wifi.setChecked(isWifiEnabled);
        wifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Switch is turned on
                    wifiManager.setWifiEnabled(true); // Enable Wi-Fi
                } else {
                    // Switch is turned off
                        wifiManager.setWifiEnabled(false); // Disable Wi-Fi
                }
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);




    }

}