package com.hfad.classmates.util;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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


public class SearchUserResult extends FirestoreRecyclerAdapter<ProfileInfo, SearchUserResult.ContactView> {
    Context context;
    public SearchUserResult(@NonNull FirestoreRecyclerOptions<ProfileInfo> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ContactView holder, int position, @NonNull ProfileInfo model) {
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
            //navigate to chat activity
            Intent intent = new Intent(context, Inside_chat.class);
            FirebaseUtil.passUserModelAsIntent(intent,model);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

    }

    @NonNull
    @Override
    public ContactView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_cover,parent,false);
        return new ContactView(view);
    }

    class ContactView extends RecyclerView.ViewHolder{
        TextView usernameText;
        TextView userInfo;
        ImageView profilePic;

        public ContactView(@NonNull View itemView) {
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