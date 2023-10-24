package com.hfad.classmates;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

        //set the profile picture, name, and email
        nameText.setText(user.getDisplayName()); //need the name
        emailText.setText(user.getEmail());

        Glide
                .with(this)
                .load(user.getPhotoUrl()).apply(RequestOptions.circleCropTransform()) //need the photo
                .into(profilePicture);

        //return our view
        return rootView;
    }
}