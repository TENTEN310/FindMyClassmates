package com.hfad.classmates;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ClassesFragment extends Fragment {

    public ClassesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_classes, container, false);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference classesCollection = db.collection("classes");
        Query query = classesCollection;

        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<Class> classes = new ArrayList<>();

            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                Class myClass = document.toObject(Class.class);
                classes.add(myClass);
            }

            for (Class myClass : classes) {

            }
        });
        return RootView;
    }
}