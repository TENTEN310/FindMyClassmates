package com.hfad.classmates;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.hfad.classmates.objectClasses.Dept;
import com.hfad.classmates.util.ShowDeptResult;

public class ClassesFragment extends Fragment {

    RecyclerView recyclerView;
    ShowDeptResult DeptHistoryResult;
    SwipeRefreshLayout swipeRefreshLayout;

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
        View view = inflater.inflate(R.layout.fragment_classes, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        recyclerView = view.findViewById(R.id.post_recycler_view);
        showDept();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showDept();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        showDept();
        return view;
    }

    public void showDept() {
        Query query = FirebaseFirestore.getInstance().collection("departments");
        FirestoreRecyclerOptions<Dept> options = new FirestoreRecyclerOptions.Builder<Dept>()
                .setQuery(query, Dept.class).build();
        ShowDeptResult DeptHistoryResult = new ShowDeptResult(options, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(DeptHistoryResult);
        DeptHistoryResult.startListening();
    }



    @Override
    public void onStart() {
        super.onStart();
        if(DeptHistoryResult!=null)
            DeptHistoryResult.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(DeptHistoryResult!=null)
            DeptHistoryResult.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(DeptHistoryResult!=null)
            DeptHistoryResult.notifyDataSetChanged();
    }
}