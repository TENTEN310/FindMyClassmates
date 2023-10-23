package com.hfad.classmates;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.hfad.classmates.objectClasses.ChatroomsContainer;
import com.hfad.classmates.objectClasses.ProfileInfo;


public class ChatHistoryResult extends FirestoreRecyclerAdapter<ChatroomsContainer, ChatHistoryResult.ChatHistoryView> {
    Context context;
    public ChatHistoryResult(@NonNull FirestoreRecyclerOptions<ChatroomsContainer> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatHistoryView holder, int position, @NonNull ChatroomsContainer model) {
        Log.d("ChatHistoryResult", "onBindViewHolder called for position " + position);

        FirebaseUtil.getOtherUserFromChatroom(model.getUserIDs())
                .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Log.d("ChatHistoryResult", "Data retrieval successful for position " + position);

                        ProfileInfo otherUserModel = task.getResult().toObject(ProfileInfo.class);
                        if (otherUserModel != null) {
                            Log.d("ChatHistoryResult", "User data: " + otherUserModel.getUsername());

                            holder.usernameText.setText(otherUserModel.getUsername());
                            holder.lastMessage.setText(model.getLastMessage());
                            holder.time.setText(FirebaseUtil.reformateTime(model.getLastTimestamp()));
                        } else {
                            Log.e("ChatHistoryResult", "OtherUserModel is null for position " + position);
                        }
                    } else {
                        Log.e("ChatHistoryResult", "Failed to retrieve data for position " + position, task.getException());
                    }
                });
    }

    @NonNull
    @Override
    public ChatHistoryView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_contact_cover,parent,false);
        return new ChatHistoryView(view);
    }


    class ChatHistoryView extends RecyclerView.ViewHolder{
        TextView usernameText;
        TextView lastMessage, time;
        ImageView profilePic;

        public ChatHistoryView(@NonNull View itemView) {
            super(itemView);
            usernameText = itemView.findViewById(R.id.user_name_text);
            lastMessage = itemView.findViewById(R.id.contact_user_info);
            time = itemView.findViewById(R.id.last_message_time_text);
            //profilePic = itemView.findViewById(R.id.profile_pic_image_view);
        }
    }
}