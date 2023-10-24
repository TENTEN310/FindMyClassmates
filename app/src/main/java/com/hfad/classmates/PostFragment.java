package com.hfad.classmates;

import static com.hfad.classmates.util.FirebaseUtil.getUserID;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    String name, uid; // should get user's name, email, and uid?
    TextView upload; // uploading button

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        name = String.valueOf(view.findViewById(R.id.user)); // get the name of the user
        upload = view.findViewById(R.id.upload); // upload button
        Intent intent = getActivity().getIntent();
        uid = getUserID();
        // Retrieving the user data like name ,email and profile pic using query
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
//        Query query = databaseReference.orderByChild("uid").equalTo(uid);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = "" + post.getText().toString().trim();
                // If description is empty set error
                if (TextUtils.isEmpty(description)) {
                    post.setError("Description Cant be empty");
                } else {
                    uploadData(description);
                }
            }
        });
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    // Upload the value of blog data into firebase
    private void uploadData(final String description) {
        final String timestamp = String.valueOf(System.currentTimeMillis());
        HashMap<Object, String> hashMap = new HashMap<>();
        hashMap.put("uid", uid);
        hashMap.put("uname", name);
        hashMap.put("description", description);
        hashMap.put("time", timestamp);
        hashMap.put("like", "0");
        hashMap.put("comments", "0");

        // set the data into firebase and then empty the description data
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Posts");
        databaseReference.child(timestamp).setValue(hashMap);

    }

}