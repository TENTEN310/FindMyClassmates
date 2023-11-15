package com.hfad.classmates.util;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.hfad.classmates.chatsActivity.Inside_chat;
import com.hfad.classmates.R;
import com.hfad.classmates.objectClasses.Post;
import com.hfad.classmates.objectClasses.ProfileInfo;

import java.util.HashMap;
import java.util.Map;


public class ShowPostResult extends FirestoreRecyclerAdapter<Post, ShowPostResult.PostView> {
    Context context;
    public ShowPostResult(@NonNull FirestoreRecyclerOptions<Post> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull PostView holder, int position, @NonNull Post model) {
        String postId = getSnapshots().getSnapshot(position).getId();
        String userId = FirebaseUtil.getUserID();
        holder.post_info.setText(model.getPostContent());
        holder.likes.setText(String.valueOf(model.getLikes()));
        holder.time.setText(FirebaseUtil.reformateDateAndTime(model.getTimestamp()));
        String posterID = model.getUserID();
        FirebaseUtil.getOtherUserDetails(posterID).get().addOnSuccessListener(documentSnapshot -> {
            ProfileInfo profileInfo = documentSnapshot.toObject(ProfileInfo.class);
            holder.usernameText.setText(profileInfo.getUsername());
        });

        FirebaseUtil.getProfilePic(model.getUserID()).getDownloadUrl()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Uri uri  = task.getResult();
                        Glide.with(context).load(uri).apply(RequestOptions.circleCropTransform()).into(holder.profilePic);
                    }
                });
        holder.likeButton.setOnClickListener(v -> {
            DocumentReference postRef = FirebaseUtil.getPostsCollectionReference().document(postId);
            DocumentReference likerRef = postRef.collection("likers").document(userId);

            likerRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    postRef.update("likes", FieldValue.increment(-1));
                    likerRef.delete();
                    holder.likes.setText(String.valueOf(model.getLikes() - 1));
                } else {
                    postRef.update("likes", FieldValue.increment(1));
                    Map<String, Boolean> liked = new HashMap<>();
                    liked.put("liked", true);
                    likerRef.set(liked);
                    holder.likes.setText(String.valueOf(model.getLikes() + 1));
                }
            });
        });

        holder.profilePic.setOnClickListener(v -> {
            // Inflate the popup layout
            View popupView = LayoutInflater.from(context).inflate(R.layout.user_pop_window, null);
            ImageView profileImageView = popupView.findViewById(R.id.profile_image);
            TextView userInfo = popupView.findViewById(R.id.userName);
            TextView major = popupView.findViewById(R.id.Major);
            TextView school = popupView.findViewById(R.id.School);
            ImageButton close = popupView.findViewById(R.id.imageButton3);
            close.setVisibility(View.GONE);
            Button message = popupView.findViewById(R.id.Message);

            message.setOnClickListener(v12 -> {
                Intent intent = new Intent(context, Inside_chat.class);
                intent.putExtra("userId", posterID);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            });

            FirebaseUtil.getProfilePic(posterID).getDownloadUrl().addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Uri uri  = task.getResult();
                    Glide.with(context).load(uri).apply(RequestOptions.circleCropTransform()).into(profileImageView);
                }
            });

            FirebaseUtil.getOtherUserDetails(posterID).get().addOnSuccessListener(documentSnapshot -> {
                ProfileInfo profileInfo = documentSnapshot.toObject(ProfileInfo.class);
                if(posterID.equals(FirebaseUtil.getUserID())){
                    message.setEnabled(false);
                    userInfo.setText("Myself" + "(" + profileInfo.getYear() + ")");
                    major.setText(profileInfo.getMajor());
                    school.setText(profileInfo.getSchool());
                }
                userInfo.setText(profileInfo.getUsername() + "(" + profileInfo.getYear() + ")");
                major.setText(profileInfo.getMajor());
                school.setText(profileInfo.getSchool());
            });

            // Create and show the PopupWindow
            PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        });

    }

    @NonNull
    @Override
    public PostView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_cover,parent,false);
        return new PostView(view);
    }

    class PostView extends RecyclerView.ViewHolder{
        TextView usernameText;
        TextView post_info, likes, time;
        ImageView profilePic;
        ImageButton likeButton;

        public PostView(@NonNull View itemView) {
            super(itemView);
            usernameText = itemView.findViewById(R.id.user_name_text);
            post_info = itemView.findViewById(R.id.post_info);
            profilePic = itemView.findViewById(R.id.profile_pic_image_view);
            likes = itemView.findViewById(R.id.likes);
            likeButton = itemView.findViewById(R.id.imageButton);
            time = itemView.findViewById(R.id.dateTime);
        }
    }
    @Override
    public void onDataChanged() {
        super.onDataChanged();
        notifyDataSetChanged();
    }
}