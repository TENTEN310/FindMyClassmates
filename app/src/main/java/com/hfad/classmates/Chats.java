package com.hfad.classmates;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class Chats extends Fragment {
    ImageButton contactSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize the contactSearch button here
        contactSearch = view.findViewById(R.id.chat_search_btn);
        contactSearch.setOnClickListener((v) -> {
            if(getActivity() != null) {
                startActivity(new Intent(getActivity(), Contact_search.class));
            }
        });
    }

//    void setupRecyclerView(){
//
//        Query query = FirebaseUtil.allChatroomCollectionReference()
//                .whereArrayContains("userIds",FirebaseUtil.currentUserId())
//                .orderBy("lastMessageTimestamp",Query.Direction.DESCENDING);
//
//        FirestoreRecyclerOptions<Chats> options = new FirestoreRecyclerOptions.Builder<Chats>()
//                .setQuery(query,Chats.class).build();
//
//        adapter = new RecentChatRecyclerAdapter(options,getContext());
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(adapter);
//        adapter.startListening();
//
//    }
}