package com.hfad.classmates.chatsActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Query;
import com.hfad.classmates.R;
import com.hfad.classmates.objectClasses.ChatroomsContainer;
import com.hfad.classmates.objectClasses.ChatsContainer;
import com.hfad.classmates.objectClasses.ProfileInfo;
import com.hfad.classmates.util.ChatUserResult;
import com.hfad.classmates.util.FirebaseUtil;

import java.util.Arrays;

public class Inside_chat extends AppCompatActivity {
    ImageButton back, send;
    ImageView profileAvatar;
    TextView username;
    ProfileInfo otherProfile;
    String ChatID;

    EditText messageInput;

    ChatroomsContainer chatroomsContainer;
    ChatUserResult chatUserResult;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_chat);
        otherProfile = FirebaseUtil.getUserModelFromIntent(getIntent());
        back = (ImageButton)findViewById(R.id.back_btn);
        profileAvatar = (ImageView) findViewById(R.id.profile_pic_image_view);
        username = (TextView)findViewById(R.id.other_username);
        username.setText(otherProfile.getUsername());
        send = (ImageButton)findViewById(R.id.message_send_btn);
        messageInput = (EditText)findViewById(R.id.chat_message_input);
        recyclerView = (RecyclerView) findViewById(R.id.chat_recycler_view);
        ChatID = FirebaseUtil.getChatroomID(FirebaseUtil.getUserID(),otherProfile.getUserID());

        FirebaseUtil.getProfilePic(otherProfile.getUserID()).getDownloadUrl()
                .addOnCompleteListener(task2 -> {
                    if(task2.isSuccessful()){
                        Uri uri  = task2.getResult();
                        Glide.with(this).load(uri).apply(RequestOptions.circleCropTransform()).into(this.profileAvatar);
                    }
                });

        FirebaseUtil.getChatroomReference(ChatID).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                chatroomsContainer = task.getResult().toObject(ChatroomsContainer.class);
                if(chatroomsContainer == null){
                    chatroomsContainer = new ChatroomsContainer();
                    chatroomsContainer.setChatroomID(ChatID);
                    chatroomsContainer.setUserIDs(Arrays.asList(FirebaseUtil.getUserID(),otherProfile.getUserID()));
                    chatroomsContainer.setLastTimestamp(Timestamp.now());
                    FirebaseUtil.getChatroomReference(ChatID).set(chatroomsContainer);
                }

            }
        });

        back.setOnClickListener((v)->{
            chatUserResult.stopListening();
            finish();
        });

        send.setOnClickListener(v -> {
            String message = messageInput.getText().toString().trim();
            if(message.isEmpty()) return;
            else{
                chatroomsContainer.setLastTimestamp(Timestamp.now());
                chatroomsContainer.setLastMessageUserID(FirebaseUtil.getUserID());
                chatroomsContainer.setLastMessage(message);
                FirebaseUtil.getChatroomReference(ChatID).set(chatroomsContainer);
                ChatsContainer chatsContainer = new ChatsContainer(message,FirebaseUtil.getUserID(),Timestamp.now());
                FirebaseUtil.getChatroomReference(ChatID).collection("chats").add(chatsContainer).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        messageInput.setText("");
                    }
                });
            }
        });
        showMessage();
    }

    void showMessage(){

        Query query = FirebaseUtil.getChatroomMessageReference(ChatID)
                .orderBy("timestamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ChatsContainer> options = new FirestoreRecyclerOptions.Builder<ChatsContainer>()
                .setQuery(query,ChatsContainer.class).build();

        chatUserResult = new ChatUserResult(options, getApplicationContext());
        LinearLayoutManager manager = new LinearLayoutManager(Inside_chat.this);
        manager.setReverseLayout(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(chatUserResult);
        chatUserResult.startListening();
        chatUserResult.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.smoothScrollToPosition(0);
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        chatUserResult.stopListening();
    }
}