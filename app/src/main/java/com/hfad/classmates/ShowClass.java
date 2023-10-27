package com.hfad.classmates;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.hfad.classmates.objectClasses.Classes;
import com.hfad.classmates.util.ShowClassResult;



public class ShowClass extends AppCompatActivity {
    ImageButton back;
    RecyclerView recyclerView;
    ShowClassResult ClassHistoryResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_class_result);
        String deptID = getIntent().getStringExtra("dept");
        back = findViewById(R.id.back_btn);
        recyclerView = findViewById(R.id.post_recycler_view);
        back.setOnClickListener(v -> finish());
        showClass(deptID);
    }
    public void showClass(String deptID) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query classesQuery = db.collection("departments").document(deptID).collection("classes");
        FirestoreRecyclerOptions<Classes> options = new FirestoreRecyclerOptions.Builder<Classes>()
                .setQuery(classesQuery, Classes.class)
                .build();
        ShowClassResult ClassHistoryResult = new ShowClassResult(options, getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(ClassHistoryResult);
        ClassHistoryResult.startListening();
    }

}