package com.example.lla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Profile_info extends AppCompatActivity {
private TextView updatename,updateemail,updatephoneno;
private ImageButton imageshow;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri filePath;
    String UserId;
Button back,update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);

        imageshow = findViewById(R.id.add);
        update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(filePath!=null){
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profile_images").child(UserId+".jpg");

                    storageReference.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(UserId);
                                    databaseReference.child("profileImageUrl").setValue(uri.toString());
                                    Toast.makeText(Profile_info.this, "Image Updated", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                }

            }
        });
        back = findViewById(R.id.button2);
        back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       Intent anv = new Intent(Profile_info.this,MainActivity.class);
       startActivity(anv);
       finish();
    }
    });
        imageshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Profile_info.this, "Upload your image", Toast.LENGTH_SHORT).show();
                chooseImage(v);
            }
        });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
         UserId = user.getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(UserId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String phoneNumber = dataSnapshot.child("phoneno").getValue(String.class);
                    String image = dataSnapshot.child("profileImageUrl").getValue(String.class);

                    updateemail = findViewById(R.id.updateName);
                    updatename = findViewById(R.id.updateemail);
                    updatephoneno = findViewById(R.id.updatephone);
                    imageshow = findViewById(R.id.add);// Replace with your TextView's ID
                    if(image !=null && !image.isEmpty())
                    {
                        Picasso.get()
                                .load(image)
                                .placeholder(R.drawable.download)
                                .error(R.drawable.progress_drawable)
                                .into(imageshow);
                    }
                    updateemail.setText(name);
                    updatename .setText(email);
                    updatephoneno.setText(phoneNumber);
                } else {
                    // Handle the case where the data doesn't exist or the user ID is invalid.
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors that may occur.
            }
        });

    }
    public void chooseImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageshow.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}