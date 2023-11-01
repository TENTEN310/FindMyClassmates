package com.hfad.classmates.chatsActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.hfad.classmates.R;
import com.hfad.classmates.objectClasses.ChatroomsContainer;
import com.hfad.classmates.util.ChatHistoryResult;
import com.hfad.classmates.util.FirebaseUtil;

import org.w3c.dom.Text;

public class Chats extends Fragment {
    ImageButton contactSearch;
    SwipeRefreshLayout swipeRefreshLayout;
    ChatHistoryResult chatHistoryResult;
    RecyclerView recyclerView;
    TextView noChat;

    public Chats() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_chats, container, false);
        contactSearch = view.findViewById(R.id.chat_search_btn);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        noChat = view.findViewById(R.id.emptyChat);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                showHistory(noChat);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        contactSearch.setOnClickListener((v) -> {
            if(getActivity() != null) {
                startActivity(new Intent(getActivity(), Contact_search.class));
            }
        });
        recyclerView = view.findViewById(R.id.search_user_recycler_view);
        showHistory(noChat);
        return view;
    }


    public void showHistory(TextView noChat){
        Query query = FirebaseUtil.allChatroomCollectionReference()
                .whereArrayContains("userIDs",FirebaseUtil.getUserID())
                .orderBy("lastTimestamp",Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ChatroomsContainer> options = new FirestoreRecyclerOptions.Builder<ChatroomsContainer>()
                .setQuery(query,ChatroomsContainer.class).build();

        chatHistoryResult = new ChatHistoryResult(options,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        chatHistoryResult.setEmptyView(noChat);
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