package com.example.lla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    private    EditText email, username, password, cpassword;
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button go_login;



        go_login = findViewById(R.id.go_login);
        email = findViewById(R.id.Cemail);
        username = findViewById(R.id.Rusername);
        password = findViewById(R.id.Rpassword);
        cpassword = findViewById(R.id.Cpasword);
        go_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the values entered by the user
                String userEmail = email.getText().toString();
                String userUsername = username.getText().toString();
                String userPassword = password.getText().toString();
                String confirmPassword = cpassword.getText().toString();
                String userPhoneNumber = "1234567890";
                // Perform validation checks
                if (!isValidEmail(userEmail)) {
                    // Invalid email
                    email.setError("Invalid email address");
                } else if (userUsername.isEmpty() || userUsername.matches(".*\\d.*")) {
                    // Username is empty or contains a number
                    username.setError("Invalid username");
                } else if (userPassword.isEmpty() || userPassword.length() < 8 || !userPassword.equals(confirmPassword)) {
                    // Password is empty or doesn't match the confirmation
                    password.setError("Password must be at least 8 characters long and match the confirmation");
                    cpassword.setError("Password must be at least 8 characters long and match the confirmation");
                } else {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(userEmail, userPassword)
                            .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        mDatabase = FirebaseDatabase.getInstance().getReference();

                                        User users = new User(userEmail,userPhoneNumber,userUsername);
                                        // User registration successful
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        String userId = user.getUid();

                                        mDatabase.child("users").child(userId).setValue(users);
                                        Toast.makeText(Register.this, "Registration Done. Please Login.", Toast.LENGTH_SHORT).show();
                                        // Proceed to the login activity
                                        Intent b = new Intent(Register.this, Login.class);
                                        startActivity(b);
                                        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
                                    } else {
                                        // User registration failed
                                        Toast.makeText(Register.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    // Function to validate email format
    private boolean isValidEmail(String email) {
        // You can use a regular expression or Android's built-in method
        // Here, we use Android's built-in method
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


}