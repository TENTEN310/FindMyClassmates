package com.hfad.classmates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.hfad.classmates.objectClasses.ProfileInfo;
import com.hfad.classmates.util.FirebaseUtil;
import com.hfad.classmates.util.RosterUserResult;

public class Roster extends AppCompatActivity {
    RosterUserResult rosterUserResult;
    ImageButton back;
    TextView emptyNotice;

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roster);
        back = findViewById(R.id.back_btn);
        recyclerView = findViewById(R.id.post_recycler_view);
        emptyNotice = findViewById(R.id.emptyRoster);
        showSearchResultRoster(getIntent().getStringExtra("classID"), emptyNotice);
        back.setOnClickListener(v -> finish());
    }

    void showSearchResultRoster(String classID, TextView emptyNotice) {
        String deptID = FirebaseUtil.GetDeptFromClassID(classID);
        Query query = FirebaseFirestore.getInstance()
                .collection("departments")
                .document(deptID)
                .collection("classes")
                .document(classID)
                .collection("userList");

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    FirestoreRecyclerOptions<ProfileInfo> options = new FirestoreRecyclerOptions.Builder<ProfileInfo>()
                            .setQuery(query, ProfileInfo.class).build();

                    if (rosterUserResult != null) {
                        rosterUserResult.updateOptions(options);
                    } else {
                        rosterUserResult = new RosterUserResult(options, getApplicationContext());
                        recyclerView.setLayoutManager(new LinearLayoutManager(this));
                        recyclerView.setAdapter(rosterUserResult);
                        rosterUserResult.setEmptyView(emptyNotice);
                    }
                    rosterUserResult.startListening();
                } else {
                    // Handle the case where the query returns no results
                    Toast.makeText(getApplicationContext(), "No users found for this class.", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Handle any errors that occurred while executing the query
                Toast.makeText(getApplicationContext(), "Error fetching users.", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        if(rosterUserResult!=null)
            rosterUserResult.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(rosterUserResult!=null)
            rosterUserResult.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(rosterUserResult!=null)
            rosterUserResult.startListening();
    }

}