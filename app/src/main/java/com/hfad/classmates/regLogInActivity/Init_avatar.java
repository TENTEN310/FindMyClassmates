package com.hfad.classmates.regLogInActivity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.UploadTask;
import com.hfad.classmates.util.FirebaseUtil;
import com.hfad.classmates.MainActivity;
import com.hfad.classmates.R;
import com.hfad.classmates.objectClasses.ProfileInfo;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Init_avatar extends AppCompatActivity {
    FloatingActionButton pictureBTN;
    Uri selectedImageUri = null;
    Button nextBtn;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_avatar);
        ActivityResultLauncher<Intent> imagePicker;
        pictureBTN = findViewById(R.id.button_init);
        imageView = findViewById(R.id.init_avatar);
        nextBtn = findViewById(R.id.avatar_nextBtn);
        imagePicker = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if(data!=null && data.getData()!=null){
                            selectedImageUri = data.getData();
                            Glide.with(this).load(selectedImageUri).apply(RequestOptions.circleCropTransform()).into(imageView);
                        }
                    }
                }
        );

        nextBtn.setOnClickListener((v)->{
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });

        pictureBTN.setOnClickListener((v)->{
            ImagePicker.with(this).cropSquare().compress(512).maxResultSize(512,512)
                    .createIntent(new Function1<Intent, Unit>() {
                        @Override
                        public Unit invoke(Intent intent) {
                            imagePicker.launch(intent);
                            return null;
                        }
                    });
        });

        nextBtn.setOnClickListener(v -> {
            if (selectedImageUri == null) {
                Toast.makeText(this, "Please upload a profile picture.", Toast.LENGTH_SHORT).show();
            } else {
                FirebaseUtil.getUserDetails().get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {
                            ProfileInfo profileInfo = document.toObject(ProfileInfo.class);
                            if (profileInfo != null) {
                                Toast.makeText(this, "Welcome! " + profileInfo.getUsername(), Toast.LENGTH_SHORT).show();

                                FirebaseUtil.getUserStorage().putFile(selectedImageUri)
                                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                // When the image is successfully uploaded, then proceed
                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {
                                                // Handle unsuccessful uploads
                                                Toast.makeText(getApplicationContext(), "Failed to upload the image.", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                Toast.makeText(this, "Failed to get user profile.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "No such document.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Failed to get user details.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



    }
}

