package com.hfad.classmates.util;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.hfad.classmates.R;
import com.hfad.classmates.objectClasses.ChatsContainer;


public class ChatUserResult extends FirestoreRecyclerAdapter<ChatsContainer, ChatUserResult.ChatView> {
    Context context;
    public ChatUserResult(@NonNull FirestoreRecyclerOptions<ChatsContainer> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatView holder, int position, @NonNull ChatsContainer model) {
        if(model.getSenderId().equals(FirebaseUtil.getUserID())){
            holder.left.setVisibility(View.GONE);
            holder.right.setVisibility(View.VISIBLE);
            holder.rightTextview.setText(model.getMessage());
        }
        else{
            holder.right.setVisibility(View.GONE);
            holder.left.setVisibility(View.VISIBLE);
            holder.leftTextview.setText(model.getMessage());
        }
    }

    @NonNull
    @Override
    public ChatView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_chat,parent,false);
        return new ChatView(view);
    }

    class ChatView extends RecyclerView.ViewHolder{
        LinearLayout right, left;
        TextView leftTextview,rightTextview;
        public ChatView(@NonNull View itemView) {
            super(itemView);
            left = itemView.findViewById(R.id.left_chat_layout);
            right = itemView.findViewById(R.id.right_chat_layout);
            leftTextview = itemView.findViewById(R.id.left_chat_textview);
            rightTextview = itemView.findViewById(R.id.right_chat_textview);

        }
    }
}
