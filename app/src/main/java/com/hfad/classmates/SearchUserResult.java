package com.hfad.classmates;


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
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
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
            //profilePic = itemView.findViewById(R.id.profile_pic_image_view);
        }
    }
}
