package com.hfad.classmates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.hfad.classmates.objectClasses.ProfileInfo;
import com.hfad.classmates.util.FirebaseUtil;
import com.hfad.classmates.util.SearchUserResult;

import java.util.ArrayList;
import java.util.List;

public class Roster extends AppCompatActivity {
    SearchUserResult searchUserResult;
    ImageButton back;

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roster);
        back = findViewById(R.id.back_btn);
        recyclerView = findViewById(R.id.post_recycler_view);
        showSearchResultRoster(getIntent().getStringExtra("classID"));
        back.setOnClickListener(v -> finish());
    }

    void showSearchResultRoster(String classID) {
        String deptID = FirebaseUtil.GetDeptFromClassID(classID);

        // Corrected query path
        Query query = FirebaseFirestore.getInstance()
                .collection("departments")
                .document(FirebaseUtil.GetDeptFromClassID(classID))
                .collection("classes")
                .document(classID)
                .collection("userList");

        FirestoreRecyclerOptions<ProfileInfo> options = new FirestoreRecyclerOptions.Builder<ProfileInfo>()
                .setQuery(query, ProfileInfo.class).build();

        if (searchUserResult != null) {
            searchUserResult.updateOptions(options);
        } else {
            searchUserResult = new SearchUserResult(options, getApplicationContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(searchUserResult);
        }
        searchUserResult.startListening();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(searchUserResult!=null)
            searchUserResult.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(searchUserResult!=null)
            searchUserResult.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(searchUserResult!=null)
            searchUserResult.startListening();
    }
}