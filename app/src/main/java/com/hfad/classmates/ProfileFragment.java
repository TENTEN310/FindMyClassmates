package com.hfad.classmates;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //initialize the rootView
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        //initialize the variables
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        TextView nameText = rootView.findViewById(R.id.usernameText);
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
            .addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Uri uri  = task.getResult();
                    Glide.
                        with(this).
                        load(uri).
                        apply(RequestOptions.circleCropTransform()).
                        into(profilePicture);
                }
            });

        //update username
        ImageView changeUsername = rootView.findViewById(R.id.changeUsername);
        changeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Change Username:");

                final EditText usernameEditText = new EditText(getContext());
                usernameEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(usernameEditText);

                // save the updates
                builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newUsername = usernameEditText.getText().toString();
                        DocumentReference usersDocRef = db.collection("users").document(user.getUid());

                        usersDocRef.update("username", newUsername)
                            .addOnSuccessListener(new OnSuccessListener<Void>() { //success to change
                                @Override
                                public void onSuccess(Void aVoid) {
                                    nameText.setText(newUsername);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() { //fail to change, maybe duplicate?
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                }
                            });
                    }
                });

                // cancel the updates
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
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

                    }
                });

                // select from camera
                builder.setNeutralButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });

        //return our view
        return rootView;
    }
}