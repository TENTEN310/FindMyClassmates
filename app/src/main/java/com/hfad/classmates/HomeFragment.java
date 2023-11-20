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
import com.google.firebase.firestore.Query;
import com.hfad.classmates.objectClasses.Post;
import com.hfad.classmates.util.FirebaseUtil;
import com.hfad.classmates.util.ShowPostResult;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    ShowPostResult postHistoryResult;
    SwipeRefreshLayout swipeRefreshLayout;

    public HomeFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        recyclerView = view.findViewById(R.id.postHome_recycler_view);
        showPost();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            showPost();
            swipeRefreshLayout.setRefreshing(false);
        });

        return view;
    }


    public void showPost() {
        // Note: The get() method has been removed from the line below
        Query query = FirebaseUtil.getPostsCollectionReference()
                .orderBy("timestamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Post> options = new FirestoreRecyclerOptions.Builder<Post>()
                .setQuery(query, Post.class).build();

        postHistoryResult = new ShowPostResult(options, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(postHistoryResult);
        postHistoryResult.startListening();
    }


    @Override
    public void onStart() {
        super.onStart();
        if(postHistoryResult!=null)
            postHistoryResult.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(postHistoryResult!=null)
            postHistoryResult.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(postHistoryResult!=null)
            postHistoryResult.notifyDataSetChanged();
    }
}