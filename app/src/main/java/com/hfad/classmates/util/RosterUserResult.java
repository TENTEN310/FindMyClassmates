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
import com.hfad.classmates.chatsActivity.Inside_chat;
import com.hfad.classmates.R;
import com.hfad.classmates.objectClasses.ProfileInfo;


public class RosterUserResult extends FirestoreRecyclerAdapter<ProfileInfo, RosterUserResult.RosterView> {
    Context context;
    public RosterUserResult(@NonNull FirestoreRecyclerOptions<ProfileInfo> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull RosterView holder, int position, @NonNull ProfileInfo model) {
        holder.usernameText.setText(model.getUsername());
        String userInfoDetail = model.getMajor() + " " + model.getYear();
        holder.userInfo.setText(userInfoDetail);
        if(model.getUserID().equals(FirebaseUtil.getUserID())){
            holder.usernameText.setText(model.getUsername()+" (Me)");
        }
        FirebaseUtil.getProfilePic(model.getUserID()).getDownloadUrl()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Uri uri  = task.getResult();
                        Glide.with(context).load(uri).apply(RequestOptions.circleCropTransform()).into(holder.profilePic);
                    }
                });
        holder.itemView.setOnClickListener(v -> {
            // Inflate the popup layout
            View popupView = LayoutInflater.from(context).inflate(R.layout.user_pop_window, null);
            ImageView profileImageView = popupView.findViewById(R.id.profile_image);
            TextView userInfo = popupView.findViewById(R.id.userName);
            TextView major = popupView.findViewById(R.id.Major);
            TextView school = popupView.findViewById(R.id.School);
            ImageButton close = popupView.findViewById(R.id.imageButton3);
            Button message = popupView.findViewById(R.id.Message);

            FirebaseUtil.getProfilePic(model.getUserID()).getDownloadUrl().addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Uri uri  = task.getResult();
                    Glide.with(context).load(uri).apply(RequestOptions.circleCropTransform()).into(profileImageView);
                }
            });

            FirebaseUtil.getOtherUserDetails(model.getUserID()).get().addOnSuccessListener(documentSnapshot -> {
                ProfileInfo profileInfo = documentSnapshot.toObject(ProfileInfo.class);
                if(model.getUserID().equals(FirebaseUtil.getUserID())){
                    message.setVisibility(View.GONE);
                    userInfo.setText("Myself" + "(" + profileInfo.getYear() + ")");
                    major.setText(profileInfo.getMajor());
                    school.setText(profileInfo.getSchool());
                }
                userInfo.setText(profileInfo.getUsername() + "(" + profileInfo.getYear() + ")");
                major.setText(profileInfo.getMajor());
                school.setText(profileInfo.getSchool());
            });

            close.setOnClickListener(v1 -> {
                popupView.setVisibility(View.GONE);
            });

            message.setOnClickListener(v12 -> {
                Intent intent = new Intent(context, Inside_chat.class);
                intent.putExtra("userId", model.getUserID());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            });

            // Create and show the PopupWindow
            PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        });

    }

    @NonNull
    @Override
    public RosterView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_cover,parent,false);
        return new RosterView(view);
    }

    class RosterView extends RecyclerView.ViewHolder{
        TextView usernameText;
        TextView userInfo;
        ImageView profilePic;

        public RosterView(@NonNull View itemView) {
            super(itemView);
            usernameText = itemView.findViewById(R.id.user_name_text);
            userInfo = itemView.findViewById(R.id.contact_user_info);
            profilePic = itemView.findViewById(R.id.profile_pic_image_view);
        }
    }
    @Override
    public void onDataChanged() {
        super.onDataChanged();
        notifyDataSetChanged();
    }
}