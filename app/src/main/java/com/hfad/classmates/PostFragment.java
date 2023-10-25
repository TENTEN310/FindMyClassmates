package com.hfad.classmates;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.hfad.classmates.objectClasses.ProfileInfo;
import com.hfad.classmates.util.FirebaseUtil;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostFragment newInstance(String param1, String param2) {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    EditText post; // description of their post
    ImageView profileAvatar;
    String uid, uname; // should get user's name, email, and uid?
    TextView upload, name; // uploading button
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        profileAvatar = view.findViewById(R.id.profile_image);
        FirebaseUtil.getProfilePic(FirebaseUtil.getUserID()).getDownloadUrl()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Uri uri = task.getResult();
                        Glide.with(this).load(uri).apply(RequestOptions.circleCropTransform()).into(this.profileAvatar);
                    }
                });
        name = view.findViewById(R.id.user); // get the name of the user
        FirebaseUtil.getUserDetails().get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ProfileInfo profileInfo = task.getResult().toObject(ProfileInfo.class);
                name.setText(profileInfo.getUsername());
            }
        });
        upload = view.findViewById(R.id.upload); // upload button
        post = view.findViewById(R.id.post);
        uid = FirebaseUtil.getUserID();
        // Retrieving the user data like name ,email and profile pic using query
        Query query = FirebaseFirestore.getInstance().collection("users")
                .whereEqualTo("uid", uid);

        upload.setOnClickListener(v -> {
            String description = post.getText().toString();
            // If description is empty set error
            if (description.isEmpty()) {
                post.setError("Description Cant be empty");
            } else {
                uploadData(description);
//                Snackbar mySnackbar = Snackbar.make(view, "uploaded post", 60);
//                mySnackbar.show();
            }
        });
        return view;
    }

    // Upload the value of blog data into firebase
    private void uploadData(final String description) {
        // To show indeterminate progress bar:
        progressBar.setVisibility(View.VISIBLE);
        final String timestamp = String.valueOf(System.currentTimeMillis());
        HashMap<Object, String> hashMap = new HashMap<>();
        hashMap.put("comment", "0");
        hashMap.put("description", description);
        hashMap.put("likes", "0");
        hashMap.put("time", timestamp);
        hashMap.put("uid", uid);
        hashMap.put("uname", uname);

        // set the data into firebase and then empty the title ,description and image data
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("posts");
        databaseReference.child(timestamp).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // hide the progress bar:
                        progressBar.setVisibility(View.GONE);
                        post.setText("");
                        Toast.makeText(getContext(), "Published", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getContext(), MainActivity.class));
                        getActivity().finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure( Exception e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_LONG).show();
                    }
                });


    }

}