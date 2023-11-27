package com.hfad.classmates.util;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hfad.classmates.chatsActivity.Inside_chat;
import com.hfad.classmates.R;
import com.hfad.classmates.objectClasses.Comments;
import com.hfad.classmates.objectClasses.ProfileInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ShowReviewResult extends FirestoreRecyclerAdapter<Comments, ShowReviewResult.CommentView> {
    Context context;
    private List<Comments> commentsList;
    public ShowReviewResult(@NonNull FirestoreRecyclerOptions<Comments> options, Context context) {
        super(options);
        this.context = context;
    }
    @Override
    protected void onBindViewHolder(@NonNull CommentView holder, int position, @NonNull Comments model) {
        String postId = getSnapshots().getSnapshot(position).getId();
        String posterID = model.getUid();
        String userId = FirebaseUtil.getUserID();
        holder.usernameText.setText(model.getSenderId());
        holder.post_info.setText(model.getMessage());
        holder.likes.setText(String.valueOf(model.getLikes()));
        holder.dislikes.setText(String.valueOf(model.getDislikes()));
        holder.overallRating.setText(String.valueOf(model.getOverall()));
        holder.workloadRating.setText(String.valueOf(model.getWorkload()));
        holder.attendanceRating.setText(String.valueOf(model.getAttendance()));
        holder.lateRating.setText(String.valueOf(model.getLate()));

        FirebaseUtil.getOtherUserDetails(posterID).get().addOnSuccessListener(documentSnapshot -> {
            ProfileInfo profileInfo = documentSnapshot.toObject(ProfileInfo.class);
            if(profileInfo.getUserID().equals(userId)){
                holder.deleteButton.setVisibility(View.VISIBLE);
            }
            else{
                holder.deleteButton.setVisibility(View.GONE);
            }
        });

        FirebaseUtil.getProfilePic(model.getUid()).getDownloadUrl()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Uri uri = task.getResult();
                        Glide.with(context).load(uri).apply(RequestOptions.circleCropTransform()).into(holder.profilePicture);
                    }
                });
        holder.likeButton.setOnClickListener(v -> {
            DocumentReference revRef = FirebaseUtil.getReviewCollectionReference(model.getClassName()).document(postId);
            DocumentReference likerRef = revRef.collection("likers").document(posterID);
            DocumentReference dislikerRef = revRef.collection("dislikers").document(posterID);
            dislikerRef.get().addOnSuccessListener(documentSnapshot -> {
                if(!documentSnapshot.exists()){
                    likerRef.get().addOnSuccessListener(documentSnapshot1 -> {
                        if (documentSnapshot1.exists()) {
                            revRef.update("likes", FieldValue.increment(-1));
                            likerRef.delete();
                            holder.likes.setText(String.valueOf(model.getLikes() - 1));
                        } else {
                            revRef.update("likes", FieldValue.increment(1));
                            Map<String, Boolean> liked = new HashMap<>();
                            liked.put("liked", true);
                            likerRef.set(liked);
                            holder.likes.setText(String.valueOf(model.getLikes() + 1));
                        }
                    });
                }
            });

        });

        holder.dislikeButton.setOnClickListener(v -> {
            DocumentReference revRef = FirebaseUtil.getReviewCollectionReference(model.getClassName()).document(postId);
            DocumentReference dislikerRef = revRef.collection("dislikers").document(posterID);
            DocumentReference likerRef = revRef.collection("likers").document(posterID);
            likerRef.get().addOnSuccessListener(documentSnapshot -> {
                if (!documentSnapshot.exists()) {
                    dislikerRef.get().addOnSuccessListener(documentSnapshot1 -> {
                        if (documentSnapshot1.exists()) {
                            revRef.update("dislikes", FieldValue.increment(-1));
                            dislikerRef.delete();
                            holder.dislikes.setText(String.valueOf(model.getDislikes() - 1));
                        } else {
                            revRef.update("dislikes", FieldValue.increment(1));
                            Map<String, Boolean> disliked = new HashMap<>();
                            disliked.put("disliked", true);
                            dislikerRef.set(disliked);
                            holder.dislikes.setText(String.valueOf(model.getDislikes() + 1));
                        }
                    });
                }
            });
        });

        holder.deleteButton.setOnClickListener(v -> {
            Toast.makeText(context, "Review Deleted", Toast.LENGTH_SHORT).show();
            FirebaseUtil.getReviewCollectionReference(String.valueOf(model.getClassName())).document(postId).delete();
        });

        holder.profilePicture.setOnClickListener(v -> {
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
    public CommentView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.review_cover,parent,false);
        return new CommentView(view);
    }

    class CommentView extends RecyclerView.ViewHolder{
        TextView usernameText, overallRating, workloadRating, attendanceRating, lateRating;
        TextView post_info, likes, dislikes, emptyView;
        ImageView profilePicture;
        ImageButton likeButton, dislikeButton, deleteButton;

        public CommentView(@NonNull View itemView) {
            super(itemView);
            usernameText = itemView.findViewById(R.id.uname);
            post_info = itemView.findViewById(R.id.commentInfo);
            profilePicture = itemView.findViewById(R.id.profile_pic_image_view);
            likes = itemView.findViewById(R.id.likes);
            likeButton = itemView.findViewById(R.id.likeButton);
            dislikes = itemView.findViewById(R.id.dislikes);
            dislikeButton = itemView.findViewById(R.id.downButton);
            overallRating = itemView.findViewById(R.id.overallRating);
            workloadRating = itemView.findViewById(R.id.workloadRating);
            attendanceRating = itemView.findViewById(R.id.attendanceRating);
            lateRating = itemView.findViewById(R.id.lateRating);
            emptyView = itemView.findViewById(R.id.emptyReview);
            deleteButton = itemView.findViewById(R.id.buttonDelete);
        }
    }
}

