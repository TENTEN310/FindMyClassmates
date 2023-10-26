package com.hfad.classmates.util;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.hfad.classmates.chatsActivity.Inside_chat;
import com.hfad.classmates.R;
import com.hfad.classmates.objectClasses.Post;
import com.hfad.classmates.objectClasses.ProfileInfo;


public class ShowPostResult extends FirestoreRecyclerAdapter<Post, ShowPostResult.PostView> {
    Context context;
    public ShowPostResult(@NonNull FirestoreRecyclerOptions<Post> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull PostView holder, int position, @NonNull Post model) {
        String postId = getSnapshots().getSnapshot(position).getId();
        holder.usernameText.setText(model.getUserName());
        holder.post_info.setText(model.getPostContent());
        holder.likes.setText(String.valueOf(model.getLikes()));
        FirebaseUtil.getProfilePic(model.getUserID()).getDownloadUrl()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Uri uri  = task.getResult();
                        Glide.with(context).load(uri).apply(RequestOptions.circleCropTransform()).into(holder.profilePic);
                    }
                });
        holder.likeButton.setOnClickListener(v -> {
            DocumentReference postRef = FirebaseUtil.getPostsCollectionReference().document(postId); // replace postId with the actual ID of your post

            postRef.update("likes", FieldValue.increment(1))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {

                        @Override
                        public void onSuccess(Void unused) {
                            holder.likes.setText(String.valueOf(model.getLikes() + 1));
                        }
                    });
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
        TextView post_info, likes;
        ImageView profilePic;
        ImageButton likeButton;

        public PostView(@NonNull View itemView) {
            super(itemView);
            usernameText = itemView.findViewById(R.id.user_name_text);
            post_info = itemView.findViewById(R.id.post_info);
            profilePic = itemView.findViewById(R.id.profile_pic_image_view);
            likes = itemView.findViewById(R.id.likes);
            likeButton = itemView.findViewById(R.id.imageButton);
        }
    }
    @Override
    public void onDataChanged() {
        super.onDataChanged();
        notifyDataSetChanged();
    }
}