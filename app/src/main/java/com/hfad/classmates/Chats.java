package com.hfad.classmates;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageButton;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.hfad.classmates.objectClasses.ChatroomsContainer;

public class Chats extends Fragment {
    ImageButton contactSearch;

    ChatHistoryResult chatHistoryResult;
    RecyclerView recyclerView;

    public Chats() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_chats, container, false);
        contactSearch = view.findViewById(R.id.chat_search_btn);
        contactSearch.setOnClickListener((v) -> {
            if(getActivity() != null) {
                startActivity(new Intent(getActivity(), Contact_search.class));
            }
        });
        recyclerView = view.findViewById(R.id.search_user_recycler_view);
        showHistory();
        return view;
    }


    public void showHistory(){
        Query query = FirebaseUtil.allChatroomCollectionReference()
                .whereArrayContains("userIds",FirebaseUtil.getUserID())
                .orderBy("lastMessageTimestamp",Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ChatroomsContainer> options = new FirestoreRecyclerOptions.Builder<ChatroomsContainer>()
                .setQuery(query,ChatroomsContainer.class).build();

        chatHistoryResult = new ChatHistoryResult(options,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(chatHistoryResult);
        chatHistoryResult.startListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(chatHistoryResult!=null)
            chatHistoryResult.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(chatHistoryResult!=null)
            chatHistoryResult.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(chatHistoryResult!=null)
            chatHistoryResult.notifyDataSetChanged();
    }
}