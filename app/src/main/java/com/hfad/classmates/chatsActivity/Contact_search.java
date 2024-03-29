package com.hfad.classmates.chatsActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.hfad.classmates.R;
import com.hfad.classmates.objectClasses.ProfileInfo;
import com.hfad.classmates.util.SearchUserResult;

public class Contact_search extends AppCompatActivity {
    ImageButton back, search;
    SearchUserResult searchUserResult;
    RecyclerView recyclerView;
    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_search);
        recyclerView = findViewById(R.id.search_user_recycler_view);
        back = findViewById(R.id.back_btn);
        search = findViewById(R.id.search_user_btn);
        input = findViewById(R.id.seach_username_input);
        back.setOnClickListener((v)->{
            finish();
        });

        search.setOnClickListener(v -> {
            String searchTerm = input.getText().toString();
            if(searchTerm.isEmpty() || searchTerm.length()<3){
                input.setError("Invalid Username");
                return;
            }
            showSearchResult(searchTerm);
        });
    }

    void showSearchResult(String term){
        term = term.toLowerCase();
        Query query = FirebaseFirestore.getInstance().collection("users")
            .whereGreaterThanOrEqualTo("username", term)
            .whereLessThanOrEqualTo("username", term + '\uf8ff');

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