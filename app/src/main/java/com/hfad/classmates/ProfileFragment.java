package com.hfad.classmates;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hfad.classmates.util.FirebaseUtil;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //initialize the rootView
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        //initialize the variables
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        TextView nameText = rootView.findViewById(R.id.nameText);
        TextView emailText = rootView.findViewById(R.id.emailText);
        ImageView profilePicture = rootView.findViewById(R.id.profilePicture);

        //set the name, email, and profile picture
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersCollection = db.collection("users");

        usersCollection.whereEqualTo("userID", user.getUid()).get()
            .addOnSuccessListener(queryDocumentSnapshots -> {
                if (!queryDocumentSnapshots.isEmpty()) {
                    DocumentSnapshot userDocument = queryDocumentSnapshots.getDocuments().get(0);
                    String username = userDocument.getString("username");
                    nameText.setText(username);
                }
            });

        emailText.setText(user.getEmail());

        FirebaseUtil.getProfilePic(user.getUid()).getDownloadUrl()
            .addOnCompleteListener(task2 -> {
                if(task2.isSuccessful()){
                    Uri uri  = task2.getResult();
                    Glide.
                        with(this).
                        load(uri).
                        apply(RequestOptions.circleCropTransform()).
                        into(profilePicture);
                }
            });

        //update profile picture
        Button changePicture = rootView.findViewById(R.id.changePicture);
        changePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Change Profile Picture:");

                // select from gallery
                builder.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //add photo from gallery
                    }
                });

                //select from camera
                builder.setNeutralButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //take a photo with camera
                    }
                });
                builder.show();
            }
        });

        //return our view
        return rootView;
    }
}